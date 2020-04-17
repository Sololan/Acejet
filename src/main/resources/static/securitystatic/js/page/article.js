var nginxUrl
var table = layui.table;
(function () {
    /**
     * 在文档加载后激活函数
     */
    var pic;
    var picList = [];
    var picListSql = [];
    var tableIns;
    var enclosureTable;
    $(document).ready(function () {
        getNginxUrl();
        registerEventHandler();
        initArticleTable();
        // initTreeSelect();
        initUpload();
        //加载栏目列表
        initSubject();

        KindEditor.ready(function (K) {
            window.editor = K.create('#editor_id', {
                width: '100%',
                height: '550px',
                filterMode: false,
                resizeType: 1,  // 2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动。
                uploadJson: '/kindeditor/upload',
                filePostName: 'file',
                allowPreviewEmoticons: true,
                allowImageUpload: true,
                afterUpload: function () {
                    this.sync();
                }
            });
        });
    });

    // =====================================================================================================================================

    /**
     * 注册事件
     */
    function registerEventHandler() {
        var searchSubject = '';
        var searchEnabled;

        //排序
        var searchSort = 1;
        layui.form.on('select(searchSort)', function (data) {
            searchSort = data.value;
        });

        layui.form.on('select(searchEnabled)', function (data) {
            searchEnabled = data.value;
        });
        layui.form.on('select(searchSubject)', function (data) {
            searchSubject = data.value;
        });
        $("#search").on('click', function () {
            if (!tableIns) {
                return;
            }
            tableIns.reload({
                // url: '/article/getArticleSPageByCondition',
                where: { //设定异步数据接口的额外参数，任意设
                    articleTitle: $('#search-title').val()
                    , articleSubtitle: $('#article-subtitle').val()
                    , articleStatus: searchEnabled
                    , sortNum: searchSort
                    , subjectId: searchSubject
                }
            });
        });
        //上传图片按钮
        $("#imgUploader").on('click', function () {
            if ($('.layui-anim-scaleSpring').val() == null) {
                layer.alert("请选择主图后再提交");
                return;
            }
            $('.wj-upload').hide();
            $("#imgUploader").hide();
            $("#waitUploader").show();
        });
        layui.form.on('switch(status)', function (data) {
            $('#status').val(Number(data.elem.checked));
        });
        layui.form.on('select(subject)', function (data) {
            $('#subjectId').val(data.value)
        });
        /*layui.form.on('radio(watermark)', function (data) {
            if (String(data.value) === '2') {
                $('#watermark').show();
            } else {
                $('#watermark').hide();
                $('#watermarkText').val('');
            }
        });*/


        //新版校验数据按钮逻辑
        $('#checkBtn').on('click', function () {


            //点击按钮判断数据是否合法
            //校验标题不能为空
            if ($("#articleTitle").val() == null || $("#articleTitle").val().trim() == "") {
                layer.alert("标题不能为空，请输入标题！");
                return;
            }

            //校验序号不为空且大于零小于9999
            if ($("#sortNum").val() == null || $("#sortNum").val().trim() == "") {
                layer.alert("序号不能为空，请输入序号！");
                return;
            } else if ($("#sortNum").val() < 0 || $("#sortNum").val() > 999) {
                layer.alert("请输入0~999之间的序号");
                return;
            } else {
                var posPattern = /^\d+$/;
                if (posPattern.test($("#sortNum").val()) == false) {
                    layer.alert("请输入正确的序号格式！");
                    return;
                }
            }

            //

            //检验是否设置了主图,是否需要主图
            if ($('#articleImgList').html().trim() != "") {
                if ($('input[name="mainImg"]:checked').val() == null) {
                    layer.alert("请设置主图！");
                    return;
                }
            }

            if ($('#subjects option:selected').val().length == 0) {
                layer.alert("栏目不能为空，请选择栏目！");
                return;
            }



            //未知
            editor.sync();
            html = document.getElementById('editor_id').value;//原生API

            layer.open({
                title: '确认提交吗？',
                type: 1,
                area: ['300px', '160px'],
                content: $('#saveBtnModel') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            });

        });


        $('#pageAddOrEdit .wj-back, #pageAddOrEdit .article-cancel,#enclosurePage .wj-back').on('click', function () {
            $('.wj-upload').show();
            $('.wj-page').hide();
            $('#enclosurePage').hide();
            $('#pageList').show();
        });

        //模态框关闭按钮
        $("#cancel").on('click', function () {
            layer.close(layer.index);
        });

        //点击确认提交功能
        $("#confirm").on('click', function () {
            //如果页面图片没有变化
            if ($('input[name="mainImg"]').length == picListSql.length) {
                //图片不变的情况下更换主图
                let picId = $('input[name="mainImg"]:checked').val();
                for (let p in picList) {
                    if (picList[p].pictureId == picId) {
                        picList[p].pictureType = 1
                    } else {
                        picList[p].pictureType = 0
                    }
                }

                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "PUT",
                    dataType: "json",
                    data: JSON.stringify({
                        id: $('#articleId').val(),
                        status: $('#status').val(),
                        subjectId: $('#subjectId').val(),
                        articleTitle: $('#articleTitle').val(),
                        articleSubtitle: $('#articleSubtitle').val(),
                        articleBrief: $('#articleBrief').val(),
                        sortNum: $('#sortNum').val(),
                        templateId: 7,
                        picList: picList,
                        content: html,
                        deleteFlag: 0
                    }),
                    url: "/article/saveArticle",
                    success: function (data) {
                        window.location.reload();
                    },
                    error: function (msg) {
                        layer.alert('失败');
                    }
                })
            } else if (picList.length !== 0) {

                //如果页面图片发生变化[即图片发生了增删]，则先获取图片类型并更改
                //插入图片后，对原本页面存在的图片无法进行修改图片状态
                //所以，我们在检查完毕之后对原本就存在的图片的状态进行修改
                let picId = $('input[name="mainImg"]:checked').val();
                for (let p in picList) {
                    if (picList[p].pictureId == picId) {
                        picList[p].pictureType = 1
                    } else {
                        picList[p].pictureType = 0
                    }
                }
                ;


                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "PUT",
                    dataType: "json",
                    data: JSON.stringify({
                        id: $('#articleId').val(),
                        status: $('#status').val(),
                        subjectId: $('#subjectId').val(),
                        articleTitle: $('#articleTitle').val(),
                        articleSubtitle: $('#articleSubtitle').val(),
                        articleBrief: $('#articleBrief').val(),
                        sortNum: $('#sortNum').val(),
                        templateId: 7,
                        picList: picList,
                        content: html,
                        deleteFlag: 0
                    }),
                    url: "/article/saveArticle",
                    success: function (data) {
                        window.location.reload();
                    },
                    error: function (msg) {
                        layer.alert('失败');
                    }
                })
            }

            //如果提交时页面没有图片，则根据文章id删除图片
            if ($('input[name="mainImg"]').length == 0) {
                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "GET",
                    url: "/article/deletePictureSByArticleId/" + $('#articleId').val(),
                    success: function (data) {
                        window.location.reload();
                    },
                    error: function (msg) {
                        window.location.reload();

                    }
                })
            }
        })

    }

    /**
     * 初始化文章列表
     */
    function initArticleTable() {

        var list;
        //获取栏目字段
        $.ajax({
            url: "/article/getAllSubjectS",
            type: "GET",
            dataType: "JSON",
            success: function (result) {
                list = result;
            }, error: function () {
                layer.alert("栏目列表获取失败!");
            }
        });

        tableIns = table.render({
            // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
            elem: '#articleTable',
            // 异步数据接口相关参数。其中 url 参数为必填项
            // url: '/demo/demo.json',
            url: '/article/getArticleSPageByCondition',
            //开启头部工具栏，并为其绑定左侧模板
            toolbar: '#toolbarTpl',
            // 该参数可自由配置头部工具栏右侧的图标按钮
            defaultToolbar: ['filter'],
            // 设定容器高度
            height: getTableHeight(),
            // 开启分页（默认：false）
            where: {
                articleTitle: ''
                , articleSubtitle: ''
                , articleStatus: ''
                , subjectId: ''
                , sortNum: ''
            },
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
                {field: 'ARTICLE_TITLE', title: '文章标题'},
                {field: 'ARTICLE_SUBTITLE', title: '文章副标题'},
                // {field: 'TEMPLATE_ID', title: '文章模板'},
                {
                    field: 'STATUS', title: '启用状态', templet: function (res) {
                        if (res.STATUS == 1) return '启用';
                        return '不启用'
                    }
                },
                {field: 'subjectName', title: '所属栏目'},
                {title: '操作', toolbar: '#barTpl', minWidth: 150}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(article)', function (obj) {
            switch (obj.event) {
                case 'addArticle':
                    $('#articleImgList').empty()
                    KindEditor.instances[0].html('')
                    $('#articleId').val('')
                    $('#articleSubtitle').val('')
                    $('#articleTitle').val('')
                    $('#articleBrief').val('')
                    $('#status').val('1')
                    $('#id').val('')
                    jumpToAddOrEdit(false);
                    break;
                default:
                    break;
            }
        });
        //文章附件工具事件
        table.on('tool(enclosureTableFilter)', function (obj) {

            let img = obj.data;

            if (obj.event === 'delEnclosure') {
                layer.confirm('确认删除吗', function (index) {
                    deleteEnclosure(obj.data.id);
                    layer.close(index);
                });
            }
        });
        //监听行工具事件
        table.on('tool(article)', function (obj) {

            var data = obj.data;
            if (obj.event === 'del') {
                layer.confirm('真的删除行么', function (index) {

                    $.ajax({
                        contentType: "application/json;charset=UTF-8",
                        type: "PUT",
                        dataType: "json",
                        data: JSON.stringify({
                            id: obj.data.ID,
                            deleteFlag: 1
                        }),
                        url: "/article/saveArticle",
                        success: function (data) {
                            layer.alert("删除文章成功！")
                        },
                        error: function (msg) {
                            layer.alert('失败')
                        }
                    })
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                var content;
                $('#articleImgList').empty()

                //编辑事件--显示已保存数据
                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "GET",
                    dataType: "json",
                    url: "/article/getArticleById/" + obj.data.ID,
                    success: function (data) {
                        KindEditor.instances[0].html(data.data.content)
                    },
                    error: function (msg) {
                        layer.alert('失败')
                    }
                })

                $('#articleId').val(obj.data.ID)
                $('#articleSubtitle').val(obj.data.ARTICLE_SUBTITLE)
                $('#articleTitle').val(obj.data.ARTICLE_TITLE)
                $('#articleBrief').val(obj.data.ARTICLE_BRIEF)
                $('#sortNum').val(obj.data.SORT_NUM)
                $("#subjects").val(obj.data.SUBJECT_ID)

                //状态显示
                if (obj.data.STATUS == 0) {
                    $('#statusP').prop('checked', false).val('1');
                } else {
                    $('#statusP').prop('checked', true).val('1');
                }

                //主图填充
                var picId = $('input[name="mainImg"]:checked').val();
                for (var p in picList) {
                    if (picList[p].pictureId == picId) {
                        picList[p].pictureType = 1
                    } else {
                        picList[p].pictureType = 0
                    }
                }


                //获取文本和图片
                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "GET",
                    dataType: "json",
                    url: "/article/getPictureSByArticleId/" + obj.data.ID,
                    success: function (data) {
                        var count = 0;
                        var m;
                        var z = 0;
                        for (var i in data.data) {
                            m = _createTr(i);
                            if (m === 0) {
                                count++;
                            } else if (m === 1) {
                                z = count;
                            }
                        }

                        $($('input[name="mainImg"]')[z]).prop("checked", "true");
                        var form = layui.form;
                        form.render('radio', 'uploadList');

                        //判断是否为主图
                        function _createTr(i) {
                            var FLAG;
                            if (data.data[i].pictureType == 1) {
                                FLAG = 1;
                            } else {
                                FLAG = 0;
                            }
                            var tr = $(['<tr id="upload-' + data.data[i].id + '">',
                                '<td><img class="wj-img" src="' + nginxUrl + data.data[i].picture + '"></td>',
                                '<td><input type="radio" name="mainImg" class="wj-main" value="upload-' + data.data[i].id + '">',
                                '<div class="layui-unselect layui-form-radio "><i class="layui-anim layui-icon "></i>',
                                '</div>',
                                '</td>',
                                '<td>已上传</td>',
                                '<td><button type="button" class="layui-btn layui-btn-xs layui-btn-danger wj-delete">删除</button></td></tr>'].join(''));

                            var pic = {
                                'picture': data.data[i].picture,
                                'pictureType': 0,
                                'pictureId': 'upload-' + data.data[i].id,
                                'watermark': 0,
                                "watermarkText": ''
                            };
                            picListSql.push(pic);
                            picList.push(pic),
                                tr.find('.wj-delete').on('click', function () {
                                    picList.splice($.inArray(pic, picList), 1);
                                    tr.remove();
                                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                                });
                            $('#articleImgList').append(tr);

                            return FLAG;
                        };
                    },

                    error: function (msg) {
                        layer.alert('失败')
                    }
                });
                layui.form.render();
                jumpToAddOrEdit(true);
            }else if (obj.event === 'enclosure') {
                jumpToEnclosure()
                $('#articleId').val(obj.data.ID)
                initUpTable();
            }
        });
    }

    function deleteEnclosure(id) {
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "GET",
            dataType: "json",
            url: "/article/file/" + id + "/delete",
            success: function (data) {
                layer.alert("删除成功");
                initUpTable();
            },
            error: function (msg) {
                layer.alert('删除失败');
            }
        })
    }

    function initUpTable() {
        enclosureTable = table.render({
            // 指定原始 table 容器的选择器或 DOM，方法渲染方式必填
            elem: '#enclosureTable',
            // 异步数据接口相关参数。其中 url 参数为必填项
            // url: '/demo/demo.json',
            url: '/article/file/'+$('#articleId').val()+'/articleId',
            // 该参数可自由配置头部工具栏右侧的图标按钮
            defaultToolbar: ['filter'],
            // 设定容器高度
            height: getTableHeight(),
            // 设置表头。值是一个二维数组。方法渲染方式必填
            parseData: function (res) { //res 即为原始返回的数据
                return {
                    "code": 0, //解析接口状态
                    "msg": '', //解析提示文本
                    "count": res.data.length, //解析数据长度
                    "data": res.data //解析数据列表
                };
            },
            cols: [[
                {type: 'numbers', title: '序号', width: 50},
                {field: 'filePath', title: '文件路径'},
                {title: '操作', toolbar: '#delEnclosureTpl', minWidth: 150}
            ]]
        });
    }

    function doneUploadFile(path) {
        //保存文章附件
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "POST",
            dataType: "json",
            data: JSON.stringify({
                fileTitle: '1',
                articleId: parseInt($('#articleId').val()),
                filePath: path,
                sortNum: 1
            }),
            url: "/article/file",
            success: function (data) {
                initUpTable();
            },
            error: function (msg) {
                layer.alert('上传失败');
            }
        })
    }

    function getTableHeight() {
        var heightOffset = $('#articleSearch').outerHeight(true) + 189;
        return 'full-' + heightOffset;
    }

    function jumpToAddOrEdit(isEdit) {
        picList = [];
        $('.wj-page').hide();
        $('#pageAddOrEdit').show().find('.wj-title').text(isEdit ? '编辑文章' : '新建文章');
    }

    function jumpToEnclosure() {
        $('.wj-page').hide();
        $('#enclosurePage').show();
    }
    //获得nginx地址
    function getNginxUrl() {
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
            },
            error: function (msg) {
                layer.alert('获取nginx地址失败')
            }
        })
    }

    function initUpload() {
        var demoListView = $('#articleImgList');

        uploadListIns = layui.upload.render({
            async: false,
            elem: '#addImg',
            url: '/banner/upload',
            multiple: true,
            auto: false,
            bindAction: '#confirm',
            data: {
                watermark: function () {
                    return 0;
                    //return $('input[name="watermark"]:checked').val();
                },
                watermarkText: function () {
                    return '';
                    //return $('#watermarkText').val();
                }
            },
            before: function () {
                var water = $('input[name="watermark"]:checked').val();
                var watermarkText = $('#watermarkText').text();
            },
            choose: function (obj) {
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function (index, file, result) {
                    var tr = $(['<tr id="upload-' + index + '">',
                        '<td><img class="wj-img" src="' + result + '"></td>',
                        '<td><input type="radio" name="mainImg" class="wj-main" value="' + index + '"></td>',
                        '<td>等待上传</td>',
                        '<td>',
                        '<button type="button" class="layui-btn layui-btn-xs wj-reload layui-hide">重传</button>',
                        '<button type="button" class="layui-btn layui-btn-xs layui-btn-danger wj-delete">删除</button>',
                        '</td>',
                        '</tr>'].join(''));

                    //单个重传
                    tr.find('.wj-reload').on('click', function () {
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.wj-delete').on('click', function () {
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                    layui.form.render('radio', 'uploadList');
                });
            }
            , allDone: function (obj) { //当文件全部被提交后，才触发

                //存储文章信息
                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "PUT",
                    dataType: "json",
                    data: JSON.stringify({
                        id: $('#articleId').val(),
                        status: $('#status').val(),
                        subjectId: $('#subjectId').val(),
                        articleTitle: $('#articleTitle').val(),
                        articleSubtitle: $('#articleSubtitle').val(),
                        articleBrief: $('#articleBrief').val(),
                        sortNum: $('#sortNum').val(),
                        templateId: 7,
                        picList: picList,
                        content: html,
                        deleteFlag: 0
                    }),
                    url: "/article/saveArticle",
                    success: function (data) {
                        window.location.reload();
                    },
                    error: function (msg) {
                        layer.alert('失败');
                    }
                })

            }
            , done: function (res, index, upload) {
                if (res.httpCode == 200) { //上传成功

                    var tr = demoListView.find('tr#upload-' + index),
                        tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');

                    var num;
                    if (index == $("input[name='mainImg']:checked").val()) {
                        num = 1;
                    } else {
                        num = 0;
                    }

                    pic = {
                        'picture': res.data,
                        'pictureType': num,
                        'watermark': 0,
                        'watermarkText': '',
                        'sortNum': $("#sortNum").val(),
                        'articleId': index
                    };

                    picList.push(pic);

                }
                return delete this.files[index]; //删除文件队列已经上传成功的文件
                this.error(index, upload);
            }
            , error: function (index, upload) {//上传失败
                var tr = demoListView.find('tr#upload-' + index),
                    tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.wj-reload').removeClass('layui-hide'); //显示重传
            }


        });

        let uploadTableIns = layui.upload.render({
            async: false,
            elem: '#addEnclosure',    //添加图片按钮
            url: '/banner/enclosure/upload',
            accept: 'file',
            method: "post",
            exts: "doc|docx|txt|pdf|ppt|pptx",
            auto: true,    //是否选完文件后自动上传。如果设定 false，那么需要设置 bindAction 参数来指向一个其它按钮提交上传
            done: function(res, index, upload){
                doneUploadFile(res.data);
            }
            // error: handler2.uploadError,
        });
    }


    //下拉列表显示栏目
    function initSubject() {
        var form = layui.form;
        $.ajax({
            url: "/article/getAllSubjectS",
            type: "GET",
            dataType: "JSON",
            success: function (result) {
                var subjectList = result;
                var subject = document.getElementById("subjects");
                var searchSubject = document.getElementById("searchSubject");
                for (let item of subjectList) {
                    var option = document.createElement("option");  //创建option
                    option.setAttribute("value", item.id);
                    option.innerText = item.subjectName;
                    subject.appendChild(option);
                    form.render("select");
                }
                ;

                for (let item of subjectList) {
                    var option = document.createElement("option");  //创建option
                    option.setAttribute("value", item.id);
                    option.innerText = item.subjectName;
                    searchSubject.appendChild(option),
                        form.render("select");
                }
                ;

            }, error: function () {
                layer.alert("栏目列表获取失败!");
            }
        });
    }
}());
