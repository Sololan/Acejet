var nginxUrl
window.pageLogic = {
    init: {
        //获取nginxUrl
        getNginxUrl: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getNginxUrl",
                success: function (data) {
                    nginxUrl = data.data
                    if (nginxUrl == undefined || nginxUrl == null) {
                        nginxUrl = '';
                    }
                    pageLogic.init.getTable()
                },
                error: function (msg) {
                    alert('获取nginx地址失败')
                }
            })
        },
        /*
        表格初始化
         */
        getTable: function () {
            var table = layui.table;
            tableIns = table.render({
                // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
                elem: '#linkTable',
                // 异步数据接口相关参数。其中 url 参数为必填项
                // url: '/demo/demo.json',
                url: '/link/getAllLinkS',
                //开启头部工具栏，并为其绑定左侧模板
                toolbar: '#toolbarTpl',
                // 该参数可自由配置头部工具栏右侧的图标按钮
                defaultToolbar: ['filter'],
                // 设定容器高度
                height: this.getTableHeight(),
                // 开启分页（默认：false）
                page: true,
                // 设置表头。值是一个二维数组。方法渲染方式必填
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": 0, //解析接口状态
                        "msg": '', //解析提示文本
                        "count": res.data.totalCount, //解析数据长度
                        "data": res.data.result //解析数据列表
                    };
                },
                cols: [[
                    {type: 'numbers', title: '序号', width: 50},
                    {field: 'linkTypeName', title: '链接分类'},
                    {field: 'linkName', title: '链接名称'},
                    {
                        field: 'picture', title: '图片', templet: function (res) {
                            return '<img src="' + nginxUrl + res.picture + '">'
                        }
                    },
                    {field: 'linkUrl', title: '链接url'},
                    // {
                    //     field: 'STATUS', title: '启用状态', templet: function (res) {
                    //         if (res.STATUS == 1) return '启用';
                    //         return '不启用'
                    //     }
                    // },
                    {field: 'sortNum', title: '序号'},
                    {title: '操作', toolbar: '#barTpl', minWidth: 150}
                    /*{
                       field: 'email', title: '邮箱', width: 150, templet: function (res) {
                           console.log(1);
                           return '<em>' + res.email + '</em>';
                       }
                   },*/
                ]]
            });

            //头工具栏事件
            table.on('toolbar(link)', function (obj) {
                switch (obj.event) {
                    case 'addLink':
                        pageLogic.fn.clearForm();
                        $('#pageList').hide();
                        $('#pageAddOrEdit').show();
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
                            url: "/link/deleteLinkById/" + data.id,
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
        /*
        链接分类初始化
         */
        initModel: function () {
            //初始化栏目
            $('#linkType').empty();
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/link/getAllLinkType",
                success: function (data) {
                    $('#linkType').append('<option>请选择链接分类</option>')
                    $.each(data.data, function (i, val) {
                        $('#linkType').append('<option value="' + val.id + '">' + val.linkTypeName + '</option>')
                    })
                    layui.form.render()
                },
                error: function (msg) {
                    alert('失败')
                }
            })
        },
    },
    /*
    注册事件
     */
    event: function () {
        /*
        注册链接分类事件
         */
        layui.form.on('select(linkType)', function (data) {
            $('#linkTypeId').val(data.value)
        });
        /*
        注册保存按钮事件
         */
        $('#saveBtn').on('click', function () {
            if ($('#linkTypeId').val() == '请选择链接分类') {
                layer.alert('请先选择链接分类再提交')
                return;
            }
            if ($('#linkName').val() == null || $('#linkName').val() == '') {
                layer.alert('请填写链接名称')
                return;
            }
            if ($('#linkUrl').val() == null || $('#linkUrl').val() == '') {
                layer.alert('请填写url')
                return;
            }
            if ($('#upPicture').val() == null || $('#upPicture').val() == '') {
                layer.alert('请上传一张图片');
                return;
            }
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "POST",
                dataType: "json",
                data: JSON.stringify({
                    id: $('#linkId').val(),
                    linkTypeId: $('#linkTypeId').val(),
                    linkName: $('#linkName').val(),
                    linkUrl: $('#linkUrl').val(),
                    sortNum: $('#sortNum').val(),
                    picture: $('#upPicture').val()
                }),
                url: "/link/saveLink",
                success: function (data) {
                    layer.alert("保存成功")
                    layui.table.reload('linkTable', {
                        url: '/link/getAllLinkS',
                        page: true
                    });
                    $('.wj-page').show();
                    $('#pageAddOrEdit').hide();
                },
                error: function (msg) {
                    layer.alert(msg.data)
                }
            })
        })
        /*
        注册取消事件
         */
        $('#cancelBtn').on('click', function () {
            $('.wj-page').show();
            $('#pageAddOrEdit').hide();
        })
        $('.wj-back').on('click', function () {
            $('.wj-page').show();
            $('#pageAddOrEdit').hide();
        })
    },
    fn: {
        /*
        根据id获取链接详情
         */
        getLinkById: function (id) {
            $('#addEditLinkTitle').text('编辑友情链接')
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/link/getLinkById/" + id,
                success: function (data) {
                    pageLogic.fn.reshowLinkForm(data.data);
                    $('.wj-page').hide();
                    $('#pageAddOrEdit').show();
                },
                error: function (msg) {
                    alert('失败')
                }
            })
        },
        reshowLinkForm: function (data) {
            $('#linkTypeId').val(data.linkTypeId);
            $('#linkType').val(data.linkTypeId);
            $('#linkUrl').val(data.linkUrl);
            $('#sortNum').val(data.sortNum);
            $('#linkName').val(data.linkName);
            $('#linkId').val(data.id);
            $('#uploader').prop('src', nginxUrl + data.picture);
            $('#upPicture').val(data.picture);
            layui.form.render();
        },
        clearForm: function () {
            $('#addEditLinkTitle').text('新增友情链接');
            $('#linkName').val('');
            $('#linkUrl').val('');
            $('#sortNum').val('');
            $('#linkName').val('');
            $('#linkId').val('');
        },
        initUpload: function () {
            //普通图片上传
            var uploadInst = layui.upload.render({
                elem: '#uploader',
                url: '/banner/upload',
                data: {
                    watermark: 0,
                    watermarkText: ''
                },
                size: 100000, //限制文件大小，单位 KB
                before: function (obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function (index, file, result) {
                        $('#uploader').attr('src', result); //图片链接（base64）
                    });
                },
                done: function (res) {
                    $('#upPicture').val(res.data)
                    //如果上传失败
                    if (res.code > 0) {
                        return layer.msg('上传失败');
                    }
                    //上传成功
                },
                error: function () {
                    //演示失败状态，并实现重传
                    var demoText = $('#uploadText');
                    demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                    demoText.find('.demo-reload').on('click', function () {
                        uploadInst.upload();
                    });
                }
            });
        }
    }
}

$(function () {
    pageLogic.init.getNginxUrl();
    pageLogic.init.initModel();
    pageLogic.fn.initUpload();
    pageLogic.event();
})

