const subSubjects_url = '/subject/getSubSubjectSPage/'
const parentSubjects_url = "/subject/getParentSubjectS";
const getSubjectById_url = "/subject/getSubjectById/";

const addSubject_url = "/subject/addSubject";
const updateSubject_url = "/subject/updateSubject";
const deleteSubject_url = "/subject/deleteSubject/";
const getMainImg_url = "/subjectPicture/getMainImg/";

let subjectList = [];
let subjectTableIns;

const table = layui.table;
const form = layui.form;

let currentPictures = [];
let watermarkText = '';
let uploadIns;
let uploadFiles;
let imgTrs = $('#imgTrs');

let pSubjectId;
let pSubject;
let selectedSubject = null;
let selectedSubjectId = -1;
let mainImg = null;
let inEditPage = null;
let editPSubject = false;

let wjNginxUrl;

window.subject = {
    init: {
        getNginxUrl: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getNginxUrl",
                success: function (data) {
                    wjNginxUrl = data.data
                    if (wjNginxUrl === undefined || wjNginxUrl === null) {
                        wjNginxUrl = '';
                    }
                },
                error: function (msg) {
                    alert('获取nginx地址失败')
                }
            })
        },

        createNavTree: function (subjects) {
            for (let i = 0; i < subjects.length; i++) {
                subjectList.push(subjects[i]);

                let aId = "subSubject-" + i;
                $("#subjectChildren").append(
                    '<dd><a href="/security/subject" id=' + aId + '>' + subjects[i].subjectName + '</a></dd>');
            }
        },

        initPSubject: function (data) {
            pSubject = data;
            $("#pageList").find(".wj-title").text(pSubject.subjectName);
        },

        initSubjectTable: function () {
            //第一个实例
            subjectTableIns = table.render({
                elem: '#subjectTable',
                height: subject.fn.getTableHeight(),
                toolbar: '#toolbarTpl',
                defaultToolbar: ['filter'],
                page: true, //开启分页
                limit: 10,
                // 设置表头。值是一个二维数组。方法渲染方式必填
                cols: [[ //表头
                    {type: 'numbers', title: '序号', width: 60, sort: true, fixed: 'left'},
                    {field: 'subjectName', title: '栏目名称'},
                    {
                        field: 'status', title: '启用状态', templet: function (res) {
                            return res.status === 1 ? "启用" : "未启用"
                        }
                    },
                    {
                        field: 'isEdit', title: '编辑状态', templet: function (res) {
                            return res.isEdit === 1 ? "可编辑" : "不可编辑"
                        }
                    },
                    {
                        field: 'isDelete', title: '删除状态', templet: function (res) {
                            return res.isDelete === 1 ? "可删除" : "不可删除"
                        }
                    },
                    {field: 'sortNum', title: '排序'},
                    {title: '操作', toolbar: '#barTpl', minWidth: 220}
                ]],
                url: subSubjects_url + pSubjectId, //数据接口   //todo 动态获取一级栏目id
                parseData: function (res) { //res 即为原始返回的数据
                    return {
                        "code": 0, //解析接口状态
                        "msg": '', //解析提示文本
                        "count": res.data.count, //解析数据长度
                        "data": res.data.subSubjects //解析数据列表
                    };
                },
            });

        }

    },

    event: function () {
        $('#pageAddOrEdit, #imgPage, #linkPage').hide();

        //头工具栏事件
        table.on('toolbar(subjectTableFilter)', function (obj) {
            switch (obj.event) {
                case 'addSubject':
                    fn.clearForm();
                    // $('#articleImgList').empty()    ////todo清空主图
                    fn.jumpToAddOrEdit(false);
                    break;
                default:
                    break;
            }
        });
        //监听行工具事件
        table.on('tool(subjectTableFilter)', function (obj) {
            let subject = obj.data;

            if (obj.event === 'delSubject') {
                layer.confirm('确认删除吗', function (index) {
                    req.deleteSubject(subject.id, obj)
                    layer.close(index);
                });
            } else if (obj.event === "manageImg") {
                init2.initImgTable(subject.id);

                selectedSubjectId = subject.id;
                fn.jumpToImgPage(subject.subjectName);

            } else if (obj.event === "manageLink") {
                pageLogic.init.getTable(subject.id);
                pageLogic.event();

                selectedSubjectId = subject.id;
                fn.jumpToLinkPage(subject.subjectName);

            } else if (obj.event === 'updateSubject') {
                let form = layui.form;
                form.val("formFilter", {
                    "subjectName": subject.subjectName,
                    "subjectSubName": subject.subjectSubName,
                    // "templateId": subject.templateId,
                    "showType": subject.showType,
                    "status": subject.status,
                    "sortNum": subject.sortNum,
                    "subjectBrief": subject.subjectBrief,
                });
                req.getMainImg(subject.id);

                selectedSubjectId = subject.id;
                fn.jumpToAddOrEdit(true);
            }
        });

        $('#updatePSubject').on('click', function () {
            let form = layui.form;
            form.val("formFilter", {
                "subjectName": pSubject.subjectName,
                "subjectSubName": pSubject.subjectSubName,
                // "templateId": pSubject.templateId,
                "showType": pSubject.showType,
                "status": pSubject.status,
                "sortNum": pSubject.sortNum,
                "subjectBrief": pSubject.subjectBrief,
            });
            req.getMainImg(pSubject.id);

            selectedSubjectId = pSubject.id;

            $("#statusItem").hide();
            $("#templateItem").hide();
            $("#showTypeItem").hide();
            $("#sortNumItem").hide();

            editPSubject = true;

            fn.jumpToAddOrEdit(true);


        })

        //返回上一级
        $('.backSubjectTable').on('click', function () {
            $('#pageAddOrEdit, #imgPage, #linkPage, #linkPageAddOrEdit').hide();
            $('#pageList').show();

            $("#statusItem").show();
            $("#templateItem").show();
            $("#showTypeItem").show();
            $("#sortNumItem").show();

            selectedSubjectId = -1;
            selectedSubject = null;
            mainImg = null;
            inEditPage = null;
            fn.clearForm();

            // $('.wj-upload').show();
        });

        form.on('submit(addSubjectFilter)', handler.addSubject);
        form.on('submit(updateSubjectFilter)', handler.updateSubject);
        form.on('submit(saveSubjectFilter)', handler.saveSubject);
        $("#reset").on("click", function () {
            $('#uploader').attr('src', "/securitystatic/img/empty_img.png"); //图片链接（base64）
            $('#upPicture').val("");
        })

        //表单自定义验证
        layui.form.verify({
            //验证主图
            sortNumVerify: function (value, item) {
                if (value < 1 || value > 20) {
                    return "请输入1-20之间的数字";
                }
            },
            mainImgVerify: function (value, item) {
                if (value === "") {
                    return "栏目主图不能为空";
                }
            },
            imgVerify: function () {
                if (imgTrs.find("td > div.layui-form-radioed").val() == null) {
                    return "请选择主图后再提交";
                }
            }
        });

        $("#templatePrev").on('click', function () {
            img_prev.src = "/securitystatic/img/template_prev.png";
            let w = $(window).width() - 100;
            let wh = $(window).height() - 100;
            let h = wh < w * 0.65 ? wh : w * 0.65;
            layer.open({
                title: '预览',
                type: 1,
                area: [w + "px", h + "px"], //宽高
                content: $('#prevModal'),
                shadeClose: true,   //点击弹层外区域关闭
                success: function (layero, index) {
                    $("#img_prev").on("click", function () {
                        layer.close(index);
                    })
                }
            });
        })
    },

    handler: {
        addSubject: function (fromData) {
            let addData = fromData.field;

            // if(selectedSubject != null){
            addData["pid"] = pSubjectId;    //todo 一级栏目id
            req.addSubject(addData)
            // }
            return false;   //阻止表单跳转
        },

        updateSubject: function (formData) {
            let updateData = formData.field;

            if (selectedSubjectId !== -1) {
                updateData["id"] = selectedSubjectId;
                req.updateSubject(updateData);
            }
            return false;   //阻止表单跳转
        },

        saveSubject: function (formData) {
            // layer.open({
            //     title: '添加栏目',
            //     type: 1,
            //     btnAlign: 'c',
            //     resize: false,
            //     content: $('#comfirmModel'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            //     success: function(layero, index){
            //         $("#cancelAdd, #confirmAdd").on("click", function () {
            //             layer.close(index);
            //         })
            //     }
            // });
            layer.confirm('确认保存栏目吗?', {title: '提示'}, function (index) {
                $("#confirmAdd").click();
                layer.close(index);
            });
            return false;
        }
    },

    req: {
        //请求一级栏目
        getParentSubjects: function (handleResponse) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: parentSubjects_url,
                success: function (response) {
                    handleResponse(response.data);
                },
                error: function (msg) {
                    layui.alert('栏目失败')
                }
            })
        },

        getSubjectById: function (pSubjectId, handleResponse) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: getSubjectById_url + pSubjectId,
                success: function (response) {
                    handleResponse(response.data);
                },
                error: function (msg) {
                    layui.alert('栏目失败')
                }
            })
        },

        addSubject: function (data) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: addSubject_url,
                data: JSON.stringify(data),
                success: function (response) {
                    layer.alert("添加成功");
                    //todo 传id, 刷新表格
                    fn.reloadSubjectTable(pSubjectId);
                },
                error: function (msg) {
                    layer.alert("添加失败");
                }
            })
        },

        updateSubject: function (data) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: updateSubject_url,
                data: JSON.stringify(data),
                success: function (response) {
                    layer.alert("修改成功");
                    //todo 传id, 刷新表格
                    fn.reloadSubjectTable(pSubjectId);
                },
                error: function (msg) {
                    layer.alert("修改失败");
                }
            })
        },

        deleteSubject: function (id, obj) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "post",
                url: deleteSubject_url + id,
                success: function (response) {
                    obj.del();  //reload
                    layer.alert("删除成功");
                },
                error: function (msg) {
                    layer.alert("删除失败");
                }
            })
        },

        getMainImg: function (subectId) {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                dataType: "json",
                type: "get",
                url: getMainImg_url + subectId,
                success: function (response) {
                    let img = response.data;
                    mainImg = img;
                    if (img != null) {
                        $('#uploader').prop('src', wjNginxUrl + img.picture);
                        $('#upPicture').val(img.picture);
                    }
                },
                error: function (msg) {
                    layer.alert("删除失败");
                }
            })
        }
    },


    fn: {
        getTableHeight: function () {
            let heightOffset = $('#articleSearch').outerHeight(true) + 189;
            return 'full-' + heightOffset;
        },

        clearForm: function () {
            $('#reset').click();
        },

        jumpToAddOrEdit: function (isEdit) {

            inEditPage = isEdit;

            picList = [];
            $('#pageList, #imgPage, #linkPage').hide();
            $('#pageAddOrEdit').show().find('.wj-title').text(isEdit ? '编辑栏目' : '添加栏目');    //todo
            // if(isEdit){
            //     $("#submitUpdate").show();
            //     $("#submitSave").hide();
            // }else{
            //     $("#submitUpdate").hide();
            //     $("#submitSave").show();
            // }
        },

        jumpToImgPage: function (subjectName) {
            $('#pageList, #pageAddOrEdit, #linkPage').hide();
            $('#imgPage').show().find('.wj-title').text(subjectName + "栏目的图片");
        },

        jumpToLinkPage: function (subjectName) {
            $('#pageList, #pageAddOrEdit, #imgPage').hide();
            $('#linkPage').show().find('.linkTitle').text(subjectName + "栏目的链接");
        },

        disabledForm: function (disabled) {
            if (disabled === true) {
                $('#submitAdd, #submitUpdate, #reset').hide();
                $('#formId input, select, textarea').prop('disabled', true)
            } else {
                $('#formId input, select, textarea').prop('disabled', false)
            }
        },

        reloadSubjectTable: function (subjectId) {
            fn.reloadTable("subjectTable", subSubjects_url + subjectId)
        },

        reloadTable: function (tableId, url) {
            layui.table.reload(tableId, {
                url: url
            });
        }
    }

}

const init = subject.init;
const handler = subject.handler;
const req = subject.req;
const fn = subject.fn;

$(function () {
    init.getNginxUrl();

    const href = window.location.href;
    const start = href.lastIndexOf("/");
    pSubjectId = href.substring(start + 1);
    req.getSubjectById(pSubjectId, init.initPSubject);

    req.getParentSubjects(init.createNavTree);
    init.initSubjectTable();
    subject.event();


})

// function reqSubSubjects(id) {
//     $.ajax({
//         contentType: "application/json;charset=UTF-8",
//         type: "GET",
//         dataType: "json",
//         url: "/test/getParentSubjectS",
//         success: function (response) {
//             handleResponse(response.data);
//         },
//         error: function (msg) {
//             alert('失败')
//         }
//     })
// }

// function createTree(subjectList) {
//     //创建树形组件
//     let tree = layui.tree;
//     //渲染
//     let inst1 = tree.render({
//         elem: '#subjectTree'  //绑定元素
//         ,data: getTreeData(subjectList)
//         ,id: 'subjectTreeId'
//         ,onlyIconControl: true
//         ,edit: ['add','del']s
//         ,operate: nodeOperateHandler
//         ,click: nodeClickHandler
//
//     });
// }

// function getTreeData(subjectList) {
//     const treeData = [];
//
//     for (let i = 0; i < subjectList.length; i++) {
//         if (subjectList[i].pid === -1) {
//             const object = subjectList[i];
//             const subSubjects = [];
//
//             for(let j = 0; j < subjectList.length; j++){
//
//                 if(subjectList[j].pid !== -1 && subjectList[j].pid === subjectList[i].id){
//                     subSubjects.push({
//                         id: subjectList[j].id,
//                         object: subjectList[j],
//                         title: subjectList[j].subjectName,
//                         children: [],
//                         spread: true
//                     })
//                 }
//             };
//
//             treeData.push({
//                 id: object.id,
//                 object: object,
//                 title: object.subjectName,
//                 children: subSubjects,
//                 spread: true
//             })
//         }
//     }
//
//     return treeData
// }

//节点点击事件
// function nodeClickHandler(obj){
//     const subject = obj.data.object;
//     selectedSubject = JSON.parse(JSON.stringify(subject));
//
//     currentPictures = [];
//     for(let picture of subject.pictures){
//         createImgTr(picture);
//     }
//
//     // if(subject.isEdit === 1){
//     //     disabledForm(false);
//     //     $('#submitUpdate, #reset').show();
//     //     $('#submitAdd').hide();
//     // }else {
//     //     disabledForm(true)
//     // }
//
//     //填充表单值
//     let form = layui.form;
//     form.val("formFilter", {
//         "subjectName": subject.subjectName,
//         "subjectSubName": subject.subjectSubName ,
//         "templateId": subject.templateId,
//         "showType": subject.showType,
//         "status": subject.status,
//         "sortNum": subject.sortNum,
//         "subjectBrief": subject.subjectBrief
//     });
// }

// function createImgTr(picture) {
//     var ck1;
//     var ck2;
//     if (picture.pictureType === 1){
//         ck1 = 'layui-form-radioed';
//         ck2 = 'layui-anim-scaleSpring';
//     }else {
//         ck1 = "";
//         ck2 = "";
//     }
//
//     let tr = $(['<tr id="upload-' + picture.id + '">',
//         '<td><img class="wj-img" src="' + picture.picture + '"></td>',
//         '<td><input type="radio" name="mainImg" class="wj-main" value="upload-' + picture.id + '">',
//         '<div class="layui-unselect layui-form-radio ' + ck1 + ' "><i class="layui-anim layui-icon ' + ck2 + '"></i>',
//         '</div>',
//         '</td>',
//         '<td>已上传</td>',
//         '<td><button type="button" class="layui-btn layui-btn-xs layui-btn-danger wj-delete">删除</button></td></tr>'].join(''));
//
//     let pic = {
//         'picture': picture.picture,
//         'pictureType': picture.pictureType,
//         'pictureId': 'upload-' + picture.id
//     };
//     currentPictures.push(pic);
//
//     tr.find('.wj-delete').on('click', function () {
//         currentPictures.splice($.inArray(pic, currentPictures), 1);
//         tr.remove();
//         uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
//     });
//     $('#imgTrs').append(tr);
//
//     layui.form.render();
// }

// function nodeOperateHandler(node){
//     let type = node.type;
//     let subject = node.data.object; //得到当前操作节点的数据
//     selectedSubject = JSON.parse(JSON.stringify(subject));
//
//     if(type === 'add'){
//         layui.tree.reload('subjectTreeId', {}); //刷新树， 掩盖创建的未命名
//
//         if(subject.isAppendChild === 1){
//             disabledForm(false);
//             $('#submitAdd, #reset').show();
//             $('#submitUpdate').hide();
//             $('#reset').click();
//
//         }else{
//             layer.alert("二级栏目不能添加子栏目");
//         }
//
//     }else if(type === 'del'){
//         reqDeleteSubject(subject.id)
//     }
// }

// function reloadTree(subjectList) {
//     //可以重载所有基础参数
//     let tree = layui.tree;
//     tree.reload('subjectTreeId', {
//         data: getTreeData(subjectList)
//     });
// }




