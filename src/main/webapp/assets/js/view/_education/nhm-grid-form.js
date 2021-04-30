var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/education/nhmGridForm',
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                // axboot.ajax 함수에 2번째 인자는 필수가 아닙니다. ajax의 옵션을 전달하고자 할때 사용합니다.
                onError: function (err) {
                    console.log(err);
                }
            }
        });

        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        var item = caller.formView01.getData();

        axboot.ajax({
            // type: 'PUT',
            type: 'POST',
            url: '/api/v1/education/nhmGridForm',
            data: JSON.stringify(item),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push("저장 되었습니다");
            }
        });
    },
    ITEM_CLICK: function (caller, act, data) {

    },
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    ITEM_DEL: function (caller, act, data) {
        caller.gridView01.delRow("selected");
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != "error") {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    }
});

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.pageButtonView.initView();
    this.searchView.initView();
    this.gridView01.initView();
    this.formView01.initView(); // 아래는 단순 선언부이므로 이 부분에 넣어줘야 initView가 실행된다.

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {

};


fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, "data-page-btn", {
            "search": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            "save": function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            "excel": function () {

            }
        });
    }
});

//== view 시작
/**
 * searchView
 */
fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document["searchView0"]);
        this.target.attr("onsubmit", "return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);");
        this.filter = $("#filter");
    },
    getData: function () {
        return {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            filter: this.filter.val()
        }
    }
});

/**
 * gridView
 */
 fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({

            showRowSelector: true,
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                { key: 'companyNm', label: COL('company.name'), width: 150, align: 'left'},
                { key: 'ceo', label: COL('company.ceo'), width: 100, align: 'center'},
                // 값을 받아오기는 Y나 N으로 받아오지만 이를 자동으로 변환 렌더링해주는 내부 프로세스가 있다.
                { key: 'useYn', label: COL('use.or.not'), align: 'center'}
            ],
            body: {
                onClick: function () {
                    // grid component 안에서의 this는 grid 자기 자신을 가리킴
                    this.self.select(this.dindex, { selectedClear: true });
                    fnObj.formView01.setData(this.item);
                },
            },
        });

        axboot.buttonClick(this, 'data-grid-view-01-btn', {
            add: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            delete: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            },
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == 'modified' || _type == 'deleted') {
            list = ax5.util.filter(_list, function () {
                /*delete this.deleted;
                return this.key;*/
                return this.id; // 삭제 부분 작동 시키려면 해당 코드 입력
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({ __created__: true }, 'last');
    },
});

/**
 * formView
 */
fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function() {
        return {useYn: 'Y'};    // 사용여부는 default로 Y
    },
    getData: function() {
        var item = {};
        // form 영역 하위에 있는 input과 select를 찾아와라.
        // 객체를 찾을 때마다 for문을 돌면서(each) 아래와 같이 로컬 변수에 담는 작업을 한다.
        // i는 순번, elem은 받아온 순수 객체
        this.target.find('input,select').each(function (i, elem) {
            var $elem = $(elem);    // $(this)를 할당하여도 동일함
            var name = $elem.data('axPath');    // data-ax-path 속성의 값을 가져올 것. 원 letter를 그대로 써도 됨
            var value = $elem.val() || '';
            item[name] = value; // 값을 추출해서 담고 결국 {id: 1, companyNm: 삼성,...}과 같은 객체로 만들어줌
        });
        return item;
    },
    setData: function(item) {
        var value;
        for (var prop in item) {
            value = item[prop] || '';
            $('[data-ax-path="' + prop + '"]').val(value);
        }
    },
    initView: function(){
        // 다른 변경 사항이 있을 때 this가 Window로 바뀔 수 있음.
        // 그래서 계속 이 객체를 가리키기 위해 미리 _this 변수로 빼 놓음. 
        var _this = this;   // 이 코드 안에서의 this는 fnObj.formView01. 이 function 내에서 사용
        
        _this.target = $('.js-form');
        // _this.model = new ax5.ui.binder();
        // _this.model.setModel({}, _this.target); // 2번째 parameter에는 form 엘리먼트

        // console.log(_this.model.get());

        // setTimeout(function )
    }
});