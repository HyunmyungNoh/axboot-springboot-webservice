var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: "GET",
            url: ["samples", "parent"],
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
        var saveList = [].concat(caller.gridView01.getData("modified"));
        saveList = saveList.concat(caller.gridView01.getData("deleted"));

        axboot.ajax({
            type: "PUT",
            url: ["samples", "parent"],
            data: JSON.stringify(saveList),
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

fnObj.selectItems = [
    {value: 'Y', text: '사용'},
    {value: 'N', text: '미사용'}
];

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
                {key: 'key', label: 'KEY', width: 120, align: 'left', editor: 'text'},
                {
                    key: undefined,
                    label: 'VALUE',
                    align: 'center',
                    columns: [
                        {
                            key: 'value',
                            label: 'DISPLAY',
                            width: 150,
                            align: 'left'
                        },
                        {
                            key: 'value',
                            label: 'TEXTAREA',
                            width: 150,
                            align: 'left',
                            editor: {type: 'textarea'}
                        }
                    ]
                },
                {
                    key: undefined,
                    label: "ETC",
                    align: "center",
                    columns: [
                        {
                            key: "etc1",
                            label: "PRICE",
                            width: 100,
                            align: "right",
                            editor: {type: 'money'}
                        },
                        {
                            key: "etc2", 
                            label: "AMOUNT", 
                            width: 80,
                            align: "right", 
                            editor: {type: 'number'},
                        },
                        {
                            key: "cost", 
                            label: "COST",
                            width: 100, 
                            align: "right",
                            formatter: function() {
                                // if(this.item.etc1 != null){
                                // return ax5.util.number(this.item.etc1 * this.item.etc2, {'money': true});
                                // } else return "";
                                var etc1 = this.item.etc1 || 0;
                                var etc2 = this.item.etc2 || 0;
                                return ax5.util.number(etc1 * etc2, {'money': true});
                            }
                        }
                    ]
                },
                {
                    key: "etc3",
                    label: "DATE", 
                    width: 100,
                     align: "center", 
                     editor: {
                         type: 'date',
                         config: {
                             content: {
                                 mode: 'year',
                                 selectMode: 'day'
                             }
                         }
                    }
                },
                {
                    key: "etc4", 
                    label: "SELECT", 
                    width: 100, 
                    align: "center",
                    formatter: function () {
                        var i = 0,
                            len = fnObj.selectItems.length,
                            value;
                        for (; i < len; i++) {
                            if (this.item.etc4 === (value = fnObj.selectItems[i].value)) {
                                break;
                            }
                        }
                        return value === 'Y' ? '사용' : '미사용';
                    },
                    editor: {
                        type: 'select',
                        config: {
                            columnKeys: {
                                optionValue: 'value',
                                optionText: 'text',
                            },
                            options: fnObj.selectItems
                        }
                    }
                }    
            ],
            body: {
                /*
                'value' 키 컬럼 중 동일한 값(value)을 가진 셀(행)을 합치겠다.
                그러나 editor 속성 가진 열은 적용되지 않으므로,
                여기서 value 키를 가진 두 개의 element 중 editor 속성을 가지지 않은 컬럼만 merge가 일어난다.
                */
                mergeCells: ['value'],
                onClick: function () {
                    this.self.select(this.dindex, {selectedClear: true});
                },
                grouping: {
                    by: ['value'],  // 셀을'value' key를 가진 열에 의해 동일 값끼리 grouping 한다.
                    columns: [
                        {
                            label: function () {    
                                // grouping을 'value'키로 했기 때문에 결국 labels에는 해당 키 보유 element의 value + SUM 문자열이 됨
                                return this.groupBy.labels.join(', ') + ' SUM';
                            },
                            colspan: 3, // 3칸 차지함
                            align: 'center',
                        },
                        {key: 'etc1', collector: 'avg', formatter: 'money', align: 'right'},
                        {key: 'etc2', collector: 'sum', formatter: 'money', align: 'right'},
                        {
                            key: 'cost',
                            collector: function () {
                                var value = 0;
                                this.list.forEach(function (n){
                                    if(!n.__isGrouping) value += (n.etc1 || 0) * (n.etc2 || 0);
                                });
                                return ax5.util.number(value, {money: true});
                            },
                            align: 'right'
                        }
                    ]
                }
            },
            footSum: [
                [
                    {label: 'SUMMARY', colspan: 3, align: 'center'},
                    {key: 'etc1', collector: 'avg', formatter: 'money', align: 'right'},
                    {key: 'etc2', collector: 'sum', formatter: 'money', align: 'right'},
                    {
                        key: 'cost',
                        collector: function() {
                            var value = 0;
                            this.list.forEach(function(n) {
                                if(!n.__isGrouping) value += (n.etc1 || 0) * (n.etc2 || 0);
                            });
                            return ax5.util.number(value, {'money': 1});
                        },
                        align: 'right'
                    }
                ]
            ]
        });

        axboot.buttonClick(this, "data-grid-view-01-btn", {
            "add": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            "delete": function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            }
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == "modified" || _type == "deleted") {
            list = ax5.util.filter(_list, function () {
                delete this.deleted;
                return this.key;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({__created__: true}, "last");
    }
});