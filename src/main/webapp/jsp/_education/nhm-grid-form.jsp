<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ax" tagdir="/WEB-INF/tags" %>
<ax:set key="title" value="${pageName}"/>
<ax:set key="page_desc" value="${PAGE_REMARK}"/>
<ax:set key="page_auto_height" value="true"/>
<ax:layout name="base">
    <jsp:attribute name="script">
        <script type="text/javascript" src="<c:url value='/assets/js/view/_education/nhm-grid-form.js' />"></script>
        <ax:script-lang key="ax.script" var = "LANG"/>
        <ax:script-lang key="ax.base" var = "COL"/>
    </jsp:attribute>
    <jsp:body>
        <ax:page-buttons></ax:page-buttons>
        <div role="page-header">
            <form name="searchView0">
                <div data-ax-tbl class="ax-search-tbl">
                    <div data-ax-tr>
                        <div data-ax-td style="width: 300px">
                            <div data-ax-td-label>회사명</div>
                            <div data-ax-td-wrap>
                                <input type="text" name="companyNm" class="js-companyNm form-control" />
                            </div>
                        </div>
                        <div data-ax-td style="width: 300px">
                            <div data-ax-td-label>대표자</div>
                            <div data-ax-td-wrap>
                                <input type="text" name="ceo" class="js-ceo form-control" />
                            </div>
                        </div>
                        <div data-ax-td style="width: 300px">
                            <div data-ax-td-label>사업자번호</div>
                            <div data-ax-td-wrap>
                                <input type="text" name="bizno" class="js-bizno form-control" />
                            </div>
                        </div>
                        <div data-ax-td style="width: 300px">
                            <div data-ax-td-label>페이징 사용</div>
                            <div data-ax-td-wrap>
                                <ax:common-code groupCd="USE_YN" clazz="js-useYn" emptyText="전체" />
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <!-- <ax:form name="searchView0">
                <ax:tbl clazz="ax-search-tbl" minWidth="500px">
                    <ax:tr>
                        <ax:td label='ax.base.company.name' width="300px">
                            <input type="text" name="companyNm" class="js-companyNm form-control" />
                        </ax:td>
                        <ax:td label='ax.base.company.ceo' width="300px">
                            <input type="text" name="ceo" class="js-ceo form-control" />
                        </ax:td>
                        <ax:td label='ax.base.company.bizno' width="300px">
                            <input type="text" name="bizno" class="js-bizno form-control" />
                        </ax:td>
                        <ax:td label='페이징 사용' width="300px">
                            <ax:common-code groupCd="USE_YN" clazz="js-useYn-tag" emptyText="전체" />
                        </ax:td>
                    </ax:tr>
                </ax:tbl>
            </ax:form> -->
            <div class="H10"></div>
        </div>
        <div class="container-fluid"> <!-- 원래 split layout 쓰려고 했으나 이런 식으로 나눌 수 있음-->
            <div class="row">
                <div class="col-md-4">
                    <!-- 목록 -->
                    <div class="ax-button-group" data-fit-height-aside="grid-view-01">
                        <div class="left">
                            <h2><i class="cqc-list"></i>
                                거래처 목록 </h2>
                        </div>
                        <div class="right">
                            <button type="button" class="btn btn-default" data-grid-view-01-btn="add"><i class="cqc-circle-with-plus"></i> 추가</button>
                            <button type="button" class="btn btn-default" data-grid-view-01-btn="delete"><i class="cqc-circle-with-plus"></i> 삭제</button>
                        </div>
                    </div>
                    <div data-ax5grid="grid-view-01" data-fit-height-content="grid-view-01" style="height: 300px;"></div>
                </div>
                <div class="col-md-8">
                    <form name="form" class="js-form">
                        <ax:tbl clazz="ax-form-tbl" minWidth="500px">
                            <ax:tr labelWidth="150px">
                                <ax:td label="ID" width="50%">
                                    <input type="text" data-ax-path="id" class="form-control" value="" readonly="readonly"/>
                                </ax:td>
                                <ax:td label="사용여부" width="50%">
                                    <!-- data-ax-path와 dataPath? ax 서버의 dataPath 속성이 나중에 
                                    렌더링 될 때 data-ax-path로 바뀜. 즉 ax 태그 안에 있는지 여부 -->
                                    <ax:common-code groupCd="USE_YN" dataPath="useYn"/>
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="회사명" width="50%">
                                    <input type="text" data-ax-path="companyNm" class="form-control" value=""/>
                                </ax:td>
                                <ax:td label="대표자" width="50%">
                                    <input type="text" data-ax-path="ceo" class="form-control" value=""/>
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="사업자번호" width="50%">
                                    <input type="text" data-ax-path="bizno" class="form-control" value=""/>
                                </ax:td>
                                <ax:td label="전화번호" width="50%">
                                    <input type="text" data-ax-path="tel" class="form-control" value=""/>
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="우편번호" width="50%">
                                    <input type="text" data-ax-path="zip" class="form-control" value=""/>
                                </ax:td>
                                <ax:td label="주소" width="50%">
                                    <input type="text" data-ax-path="address" class="form-control" value=""/>
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="주소 상세" width="100%">
                                    <input type="text" data-ax-path="addressDetail" class="form-control" value=""/>
                                </ax:td>
                            </ax:tr>
                            <ax:tr labelWidth="150px">
                                <ax:td label="비고" width="100%">
                                    <input type="text" data-ax-path="remark" class="form-control" value=""/>
                                </ax:td>
                            </ax:tr>
                        </ax:tbl>
                    </form>

                    <!-- 위 방법은 서버에서 클래스를 불러오지 못할 때 위험하다. 그래서 순수 Html로 렌더링한 코드 또한 직접 써보기로 한다 -->
                    <!-- <div data-ax-tbl="" id="" class="ax-form-tbl">
                        <div data-ax-tr>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">ID</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="id" class="form-control" value="" readonly="readonly">
                                </div>
                            </div>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">사용여부</div>
                                <div data-ax-td-wrap>
                                    <select class="form-control null " data-ax-path="useYn">
                                        <option value="Y">사용</option>
                                        <option value="N">사용안함</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">회사명</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="companyNm" class="form-control" value="">
                                </div>
                            </div>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">대표자</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="ceo" class="form-control" value="">
                                </div>
                            </div>
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">사업자번호</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="bizno" class="form-control" value="">
                                </div>
                            </div>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">전화번호</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="tel" class="form-control" value="">
                                </div>
                            </div>
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">우편번호</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="zip" class="form-control" value="">
                                </div>
                            </div>
                            <div data-ax-td style="width:50%">
                                <div data-ax-td-label style="width:150px">주소</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="address" class="form-control" value="">
                                </div>
                            </div>
                        </div>
                        <div data-ax-tr>
                            <div data-ax-td style="width:100%">
                                <div data-ax-td-label style="width:150px">주소 상세</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="addressDetail" class="form-control" value="">
                                </div>
                            </div>
                            <div data-ax-td style="width:100%">
                                <div data-ax-td-label style="width:150px">비고</div>
                                <div data-ax-td-wrap>
                                    <input type="text" data-ax-path="remark" class="form-control" value="">
                                </div>
                            </div>
                        </div>
                    </div> -->
                </div>
            </div>
        </div>
    </jsp:body>
</ax:layout>