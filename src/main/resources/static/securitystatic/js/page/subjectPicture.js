const getImgsBySubjectId_url = '/subjectPicture/getPictureSBySubjectId/'
const addSubjectAndImg_url = "/subject/addSubjectAndPictures";
const addImgs_url = "/subjectPicture/addImgsBySubjectId";
const updateSubjectAndMainImg_url = "/subject/updateSubjectAndMainImg"

let imgTableIns;
let uploadImgs = [];

window.subjectPicture = {
    init: {
        initImgTable: function (subjectId) {
            //第一个实例
            imgTableIns = table.render({
                elem: '#imgTable',
                height: "full-350",
                defaultToolbar: ['filter'],
                page: true, //开启分页
                limit: 10,
                cols: [[ //表头
                    {type: 'numbers', title: '序号', width: 60, sort: true, fixed: 'left'},
                    // {field: 'pictureTitle', title: '图片标题'},
                    {
                        field: 'picture', title: '图片', templet: function (res) {
                            return '<img class="wj-img" src="' + wjNginxUrl + res.picture + '">'
                        }
                    },
                    // {field: 'sortNum', title: '排序'},
                    // {field: 'upload', title: '上传状态', templet: function (res) { return "上传成功"}},
                    {title: '操作', toolbar: '#imgBarTpl', minWidth: 30}
                ]],
                url: getImgsBySubjectId_url + subjectId, //数据接口   //todo
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": 0, //解析接口状态
                        "msg": '', //解析提示文本
                        "count": res.data.length, //解析数据长度
                        "data": res.data //解析数据列表
                    };
                },
            });


        },

        initMainImgUpload: function () {
            uploadIns = layui.upload.render({
                async: false,
                elem: '#uploader',    //添加图片按钮
                url: '/banner/upload',
                multiple: false, //是否允许多文件上传。设置 true即可开启。
                number: 1, //设置同时可上传的文件数量，一般配合 multiple 参数出现。
                auto: false,    //是否选完文件后自动上传。如果设定 false，那么需要设置 bindAction 参数来指向一个其它按钮提交上传
                bindAction: '#confirmAdd', //提交栏目所有信息    //todo
                data: {
                    "watermark": 0
                },
                // choose: handler2.chooseMainImg,
                choose: function (obj) {
                    for (let x in uploadFiles) {    //选择img后, 立即选择了另一张img, 需要清空uploadFiles, 保证只上传一张
                        delete uploadFiles[x];
                    }
                    uploadFiles = obj.pushFile();

                    //预读本地文件示例，不支持ie8
                    mainImg = {}; //mainImg.id = undefined
                    obj.preview(function (index, file, result) {
                        $('#uploader').attr('src', result); //图片链接（base64）
                        $('#upPicture').val(result);
                    });
                },
                done: handler2.uploadMainImgDone,
                error: handler2.uploadError,
            });
        },

        initImgUpload: function () {
            let uploadTableIns = layui.upload.render({
                async: false,
                elem: '#addImg',    //添加图片按钮
                url: '/banner/upload',
                method: "post",
                multiple: true, //是否允许多文件上传。设置 true即可开启。
                number: 6, //设置同时可上传的文件数量，一般配合 multiple 参数出现。
                auto: true,    //是否选完文件后自动上传。如果设定 false，那么需要设置 bindAction 参数来指向一个其它按钮提交上传
                data: {
                    "watermark": 0
                },
                // choose: chooseImgForTable,
                done: handler2.uploadImgDone,
                allDone: handler2.uploadImgAllDone,
                error: handler2.uploadError,
            });
        },

        initUpload: function () {
            uploadListIns = layui.upload.render({
                async: false,
                elem: '#addImg',    //添加图片按钮
                url: '/banner/upload',
                multiple: true, //是否允许多文件上传。设置 true即可开启。
                number: 6, //设置同时可上传的文件数量，一般配合 multiple 参数出现。超过不上传
                auto: true,    //是否选完文件后自动上传。如果设定 false，那么需要设置 bindAction 参数来指向一个其它按钮提交上传
                bindAction: '#confirmAdd', //提交栏目所有信息
                data: {
                    "watermark": 0
                },
                choose: chooseImgHandler,
                done: handler2.uploadDone,
                error: handler2.uploadError,
                allDone: function (obj) { //当文件全部被提交后，才触发
                    req2.addSubjectAndMainImg();
                }
            });
        },

    },

    event: function () {
        //监听行工具事件
        table.on('tool(imgTableFilter)', function (obj) {
            let img = obj.data;

            if (obj.event === 'delImg') {
                layer.confirm('确认删除吗', function (index) {
                    req2.deleteImg(img.id, obj);
                    layer.close(index);
                });
            }
        });

        $('#confirmAdd').on('click', function () {
            if (inEditPage && mainImg.id !== void 0) {    //没有更新图片, mainImg含id
                req2.updateSubjectAndMainImg();
            }
        })
    },

    handler: {
        uploadMainImgDone: function (res, index, upload) {


            if (res.httpCode === 200) { //上传成功
                let img = {
                    "subjectId": selectedSubjectId,
                    'picture': res.data,
                    'pictureType': 1,
                    "sortNum": 1,
                    // 'watermark':parseInt($("input[name='mainWatermark']:checked").val()),
                    // 'watermarkText':$("#mainWatermarkText").val(),
                };
                mainImg = img;
                if (inEditPage) {
                    req2.updateSubjectAndMainImg(img);
                } else {
                    req2.addSubjectAndMainImg(img)
                }
            }
            return delete uploadFiles[index];
        },

        uploadImgDone: function (res) {
            if (res.httpCode === 200) { //上传成功
                let img = {
                    "subjectId": selectedSubjectId,
                    'picture': res.data,
                    'pictureType': 2,
                    "sortNum": 1,
                }
                uploadImgs.push(img);
            }
        },

        uploadImgAllDone: function () {
            req2.addImgs(uploadImgs)
        },

        uploadDone: function (res, index) {

            if (res.httpCode === 200) { //上传成功
                let tr = imgTrs.find('tr#upload-' + index);
                let tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');

                img = {
                    "subjectId": selectedSubject.id,
                    'picture': res.data,
                    'pictureType': tr.find("td > div.layui-form-radioed").val() != null ? 1 : 2,   //tr中若有勾选的radio, 则为主图
                    "sortNum": 1,
                    'pictureId': index
                }
                currentPictures.push(img)

                return delete uploadFiles[index]; //删除文件队列已经上传成功的文件
            } else {
                handler2.uploadError(index)
            }

        },

        uploadError: function (index) {
            layer.alert("图片上传失败");
        }

    },

    req: {
        addSubjectAndMainImg: function (img) {
            let formData = layui.form.val("formFilter");
            const data = JSON.stringify({
                "subjectName": formData.subjectName,
                "subjectSubName": formData.subjectSubName,
                // "templateId": formData.templateId === "" ? -1 : formData.templateId,
                "showType": formData.showType,
                "status": formData.status,
                "sortNum": formData.sortNum,
                "subjectBrief": formData.subjectBrief,
                "pid": pSubjectId,   //todo 一级栏目id
                "pictures": [img]
            });

            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: addSubjectAndImg_url,
                data: data,
                success: function (res) {
                    // layer.alert(res.data);  //todo
                    fn.reloadSubjectTable(pSubjectId);   //todo 传id, 刷新表格
                    layer.alert("栏目添加成功", function (index) {
                        $('.backSubjectTable').click();
                        layer.close(index);
                    });
                },
                error: function (err) {
                    layer.alert("栏目添加失败");
                }
            })
        },

        updateSubjectAndMainImg: function (img) {
            let formData = layui.form.val("formFilter");
            const data = JSON.stringify({
                "subjectName": formData.subjectName,
                "subjectSubName": formData.subjectSubName,
                // "templateId": formData.templateId === "" ? -1 : formData.templateId,
                "showType": formData.showType,
                "status": formData.status,
                "sortNum": formData.sortNum,
                "subjectBrief": formData.subjectBrief,
                "pictures": img == null ? [] : [img],

                "id": selectedSubjectId
            });

            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: updateSubjectAndMainImg_url,
                data: data,
                success: function (res) {
                    fn.reloadSubjectTable(pSubjectId);   //todo 传id, 刷新表格
                    // layer.alert(res.data);  //todo
                    layer.alert("栏目修改成功", function (index) {
                        //yes回调
                        if (editPSubject) {
                            window.location.href = "/security/subject/" + pSubjectId;
                        } else {
                            $('.backSubjectTable').click();
                        }
                        layer.close(index);
                    });
                },
                error: function (err) {
                    layer.alert("栏目修改失败");
                }
            })
        },

        deleteImg: function (id, obj) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: "/subjectPicture/deletePictureById/" + id,
                success: function (response) {
                    obj.del();  //reload
                    layer.alert("删除成功");
                },
                error: function (msg) {
                    layer.alert("删除失败");
                }
            })
        },

        addImgs: function (data) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: addImgs_url,
                data: JSON.stringify(data),
                success: function (res) {
                    uploadImgs = [];
                    if (res.data === "图片添加成功") {
                        layer.alert("图片添加成功");
                        fn2.reloadImgTable();
                    } else {
                        layer.alert(res.data);
                    }
                },
                error: function (err) {
                    layer.alert("图片添加失败");
                }
            })
        },


    },

    fn: {
        reloadImgTable: function () {
            fn.reloadTable("imgTable", getImgsBySubjectId_url + selectedSubjectId)
        }

        // chooseImgHandler: function (obj) {
        //
        //     uploadFiles = obj.pushFile();  //将每次选择的文件追加到文件队列
        //
        //     //预读本地文件，如果是多文件，则会遍历。
        //     obj.preview(function (index, file, result, ) {
        //         //append 文件列表 DOM 的操作
        //         let tr = $(['<tr id="upload-' + index + '">',
        //             '<td><img class="wj-img" src="' + result + '"></td>',
        //             '<td><input type="radio" name="mainImg" class="wj-main" value="' + index + '"></td>',
        //             '<td>等待上传</td>',
        //             '<td>',
        //             '<button type="button" class="layui-btn layui-btn-xs wj-reload layui-hide">重传</button>',
        //             '<button type="button" class="layui-btn layui-btn-xs layui-btn-danger wj-delete">删除</button>',
        //             '</td>',
        //             '</tr>'].join(''));
        //
        //         //事件: 上传失败的单个文件重新上传
        //         tr.find('.wj-reload').on('click', function () {
        //             obj.upload(index, file);
        //         });
        //         //事件: 删除列表中对应的文件
        //         tr.find('.wj-delete').on('click', function () {
        //             delete uploadFiles[index]; //删除对应的文件
        //             tr.remove();
        //             uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
        //         });
        //         imgTrs.append(tr);
        //
        //         layui.form.render('radio', 'uploadList');   //更新 lay-filter="uploadList" 所在容器内的全部 radio 状态
        //     });
        // }

    },

}

const init2 = subjectPicture.init;
const handler2 = subjectPicture.handler;
const req2 = subjectPicture.req;
const fn2 = subjectPicture.fn;

$(function () {
    init2.initMainImgUpload();
    init2.initImgUpload();
    subjectPicture.event();
})

