window.pageLogic = {
    init: {
        /*
        表格初始化
         */
        getTable: function (nus) {
            let table = layui.table;
            let tableIns = table.render({
                // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
                elem: '#linkTable',
                // 异步数据接口相关参数。其中 url 参数为必填项
                // url: '/demo/demo.json',
                url: '/subjectLink/getLinksBySubjectId/' + nus,
                //开启头部工具栏，并为其绑定左侧模板
                toolbar: '#linkToolbarTpl',
                // 该参数可自由配置头部工具栏右侧的图标按钮
                defaultToolbar: ['filter'],
                // 设定容器高度
                height: this.getTableHeight(),
                // 开启分页（默认：false）
                page: true,
                limit: 10,
                // 设置表头。值是一个二维数组。方法渲染方式必填
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": 0, //解析接口状态
                        "msg": '', //解析提示文本
                        "count": res.data.totalCount, //解析数据长度
                        "data": res.data//解析数据列表
                    };
                },

                cols: [[
                    {type: 'numbers', title: '序号', width: 50},
                    {field: 'linkName', title: '链接名称'},
                    {
                        field: 'linkType', title: '链接类型',
                        templet: function (res) {
                            return res.linkType === 1 ? "外部链接" : "该栏目下文章列表"
                        }
                    }, //请求到的linkType: number; 元素属性: string
                    {field: 'linkUrl', title: '链接url'},
                    {field: 'sortNum', title: '序号'},
                    {title: '操作', toolbar: '#linkBarTpl', minWidth: 150}

                ]]
            });

            //头工具栏事件
            table.on('toolbar(link)', function (obj) {
                switch (obj.event) {
                    case 'addLink':
                        pageLogic.fn.clearForm();
                        $('#linkPage').hide();
                        $('#linkPageAddOrEdit').show();

                        window.pageLogic.fn.useUrlLinkType("1");

                        break;
                    default:
                        break;
                }
            });
            //监听行工具事件
            table.on('tool(link)', function (obj) {

                var data = obj.data;

                if (obj.event === 'del') {
                    layer.confirm('真的删除行么', function (index) {
                        $.ajax({
                            contentType: "application/json;charset=UTF-8",
                            type: "DELETE",
                            dataType: "json",
                            url: "/subjectLink/deleteLinkById/" + data.id,
                            success: function (data) {
                                layer.alert('删除成功')
                            },
                            error: function (msg) {
                                layui.alert('失败')
                            }
                        })
                        obj.del();
                        layer.close(index);
                    });
                } else if (obj.event === 'edit') {
                    pageLogic.fn.getLinkById(data.id)
                }
            });
        },
        getTableHeight: function () {
            var heightOffset = $('#articleSearch').outerHeight(true) + 189;
            return 'full-' + heightOffset;
        },

    },

    handler: {
        saveLink: function () {
            // if($('#linkName').val()==null||$('#linkName').val()==''){
            //     layer.alert('请填写链接名称')
            //     return;
            // }
            // if($('#linkUrl').val()==null||$('#linkUrl').val()==''){
            //     layer.alert('请填写url')
            //     return;
            // }
            // if(!pageLogic.fn.checkNum($('#sortNum').val())){
            //     layer.alert('序号只能填0——20，请重新填写')
            //     return;
            // }
            let formData = form.val("linkFormFilter");

            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "POST",
                dataType: "JSON",
                data: JSON.stringify({
                    subjectId: selectedSubjectId,
                    id: $("#linkId").val(),

                    linkName: formData.linkName,
                    linkType: formData.linkType,
                    linkUrl: formData.linkType === "1" ? formData.linkUrl : "",
                    sortNum: formData.sortNum,

                }),
                url: "/subjectLink/saveLink",
                success: function (data) {
                    layui.table.reload('linkTable', {
                        url: '/subjectLink/getLinksBySubjectId/' + selectedSubjectId,
                        page: true
                    });
                    layer.alert("保存链接成功", function (index) {
                        $('#linkPage').show();
                        $('#linkPageAddOrEdit').hide();
                        layer.close(index)
                    });

                },
                error: function (msg) {
                    // layer.alert(msg.data);alert(11);
                    layer.alert("保存链接失败")
                }
            })
        }
    },
    /*
    注册事件
     */
    event: function () {
        /*
        注册链接分类事件
         */
        /*layui.form.on('select(linkType)', function(data){
            $('#linkTypeId').val(data.value)
        });*/
        /*
        注册保存按钮事件
         */

        form.on('radio(linkTypeFilter)', function (data) {

            window.pageLogic.fn.useUrlLinkType(data.value);
        });

        form.on('submit(saveLinkFilter)', window.pageLogic.handler.saveLink);

        // $('#saveBtn').on('click', window.pageLogic.handler.saveLink());

        /*
        注册取消事件
         */
        $('#cancelBtn, #backLinkPage').on('click', function () {
            $('#linkPage').show();
            $('#linkPageAddOrEdit').hide();
        })
    },
    fn: {
        /*
        根据id获取链接详情
         */
        getLinkById: function (id) {
            $('#addEditLinkTitle').text('编辑栏目链接')
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/subjectLink/getLinkById/" + id,
                success: function (data) {
                    pageLogic.fn.reshowLinkForm(data.data);
                    $('#linkPage').hide();
                    $('#linkPageAddOrEdit').show();
                },
                error: function (msg) {
                    alert('失败')
                }
            })
        },
        reshowLinkForm: function (data) {
            $('#linkId').val(data.id);
            $('#linkUrl').val(data.linkUrl);
            $('#sortNum').val(data.sortNum);
            $('#linkName').val(data.linkName);

            window.pageLogic.fn.useUrlLinkType("" + data.linkType);
            layui.form.render();
        },
        clearForm: function () {
            $('#addEditLinkTitle').text('新增栏目链接');
            $('#linkName').val('');
            $('#linkUrl').val('');
            $('#sortNum').val('');
            $('#linkName').val('');
            $('#linkId').val('');
        },

        useUrlLinkType: function (linkType) {
            form.val("linkFormFilter", {
                "linkType": linkType, // typeof linkType: string
            });

            if (linkType === "1") {   //use url
                $("#urlItem").show();
                $("#linkUrl").attr("lay-verify", "required")
            } else if (linkType === "2") { //not
                $("#urlItem").hide();
                $("#linkUrl").attr("lay-verify", "")
            }
        },

        checkNum: function (input) {

            var nubmer = parseInt(input);

            if (nubmer == null || nubmer == "") {
                return false;
            } else if (nubmer < 0 || nubmer > 20) {
                return false;
            } else {
                var posPattern = /^\d+$/;
                if (posPattern.test(nubmer == false)) {
                    layer.alert("请输入正确的序号格式！");
                    return false;
                }
            }
            return true;
        }
    }
}

$(function () {
    // pageLogic.init.getTable(1);
    // pageLogic.event();

    // console.log(getUrlParam('picid'));
    // console.log(getUrlParam('picid'));

    // function getUrlParam(name) {
    //     //构造一个含有目标参数的正则表达式对象
    //     var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //     //匹配目标参数
    //     var r = window.location.search.substr(1).match(reg);
    //     //返回参数值
    //     if (r != null) return unescape(r[2]);
    //     return null;
    // }


})

