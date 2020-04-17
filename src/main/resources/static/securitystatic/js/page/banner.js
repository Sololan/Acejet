var nginxUrl
(function () {
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
                //给轮播列表填充值
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: "/banner/getAllBannerSView",
                    success: function (data) {
                        $('#bannerTitle1').text(data.data[0].bannerTitle)
                        $('#bannerTitle2').text(data.data[1].bannerTitle)
                        $('#bannerTitle3').text(data.data[2].bannerTitle)
                        $('#bannerTitle4').text(data.data[3].bannerTitle)
                        $('#bannerTitle5').text(data.data[4].bannerTitle)
                        $('#bannerTitle6').text(data.data[5].bannerTitle)

                        $('#articleTitle1').text('指向文章：' + data.data[0].articleTitle)
                        $('#articleTitle2').text('指向文章：' + data.data[1].articleTitle)
                        $('#articleTitle3').text('指向文章：' + data.data[2].articleTitle)
                        $('#articleTitle4').text('指向文章：' + data.data[3].articleTitle)
                        $('#articleTitle5').text('指向文章：' + data.data[4].articleTitle)
                        $('#articleTitle6').text('指向文章：' + data.data[5].articleTitle)

                        $('#picture1').attr("src", nginxUrl + data.data[0].picture)
                        $('#picture2').attr("src", nginxUrl + data.data[1].picture)
                        $('#picture3').attr("src", nginxUrl + data.data[2].picture)
                        $('#picture4').attr("src", nginxUrl + data.data[3].picture)
                        $('#picture5').attr("src", nginxUrl + data.data[4].picture)
                        $('#picture6').attr("src", nginxUrl + data.data[5].picture)

                        if (data.data[0].status == 1) {
                            $('#status1').prop('checked', true).val('1');
                        } else {
                            $('#status1').prop('checked', false).val('1');
                        }
                        if (data.data[1].status == 1) {
                            $('#status2').prop('checked', true).val('2');
                        } else {
                            $('#status2').prop('checked', false).val('2');
                        }
                        if (data.data[2].status == 1) {
                            $('#status3').prop('checked', true).val('3');
                        } else {
                            $('#status3').prop('checked', false).val('3');
                        }
                        if (data.data[3].status == 1) {
                            $('#status4').prop('checked', true).val('4');
                        } else {
                            $('#status4').prop('checked', false).val('4');
                        }
                        if (data.data[4].status == 1) {
                            $('#status5').prop('checked', true).val('5');
                        } else {
                            $('#status5').prop('checked', false).val('5');
                        }
                        if (data.data[5].status == 1) {
                            $('#status6').prop('checked', true).val('6');
                        } else {
                            $('#status6').prop('checked', false).val('6');
                        }
                        layui.form.render();

                    },
                    error: function (msg) {
                    }
                });
            },
            error: function (msg) {
                alert('获取nginx地址失败')
            }
        })
    }
    /**
     * 在文档加载后激活函数
     */
    $(document).ready(function () {

        // 初始化文章列表
        // $.ajax({
        //     contentType: "application/json;charset=UTF-8",
        //     type: "GET",
        //     dataType: "json",
        //     url: "/banner/getAllSelectableArticle",
        //     success: function (data) {
        //         // 设置下拉选择框内容
        //         var src='<option value="">请选择...</option>';
        //         console.log(data.data[0].bannerTitle);
        //         $.each(data.data,function(index,item){
        //             src+='<option value="'+item.id+'">'+item.articleTitle+'</option>';
        //         });
        //         $('#aritcleList').html(src);
        //     },
        //     error: function (msg) {
        //         alert('失败')
        //     }
        // })


        getNginxUrl();
        registerEventHandler();
        initUpload();
    });

    // =====================================================================================================================================

    /**
     * 注册事件
     */
    function registerEventHandler() {
        //监听更换文章事件
        layui.form.on('select(articleL)', function (data) {
            $('#articleId').val(data.value)
        })
        //保存设置
        $('#saveBtn').on('click', function () {
            if ($('#editBannerTitle').val() == '' || $('#upPicture').val() == '' || $('#articleId').val() == '') {
                alert('请把信息填写完整');
                return;
            }
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "POST",
                dataType: "json",
                data: JSON.stringify({
                    id: $('#idFlag').val(),
                    bannerTitle: $('#editBannerTitle').val(),
                    picture: $('#upPicture').val(),
                    articleId: -1
                }),
                url: "/banner/saveBanner",
                success: function (data) {
                    alert(data.data);
                    $('.wj-page').show();
                    $('#pageAddOrEdit').hide();
                    window.location.reload()
                },
                error: function (msg) {
                    alert('失败')
                }
            })
        })
        //轮播状态转换事件
        layui.form.on('switch(status)', function (data) {
            var flag = 0;
            if (data.elem.checked) {
                flag = 1
            }
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "PUT",
                dataType: "json",
                data: JSON.stringify({
                    id: parseInt(data.value),
                    status: parseInt(flag),
                }),
                url: "/banner/taggleBanner",
                success: function (data) {
                    alert(data.data);
                },
                error: function (msg) {
                    alert('失败')
                }
            })

            console.log(data.elem); //得到checkbox原始DOM对象
            console.log(data.elem.checked); //开关是否开启，true或者false
            console.log(data.value); //开关value值，也可以通过data.elem.value得到
            console.log(data.othis); //得到美化后的DOM对象
        });
        $('.banner-img').on('click', function () {
            var id = parseInt($(this).attr("id").replace(/[^0-9]/ig, ""))
            $.ajax({
                type: "GET",
                url: "/banner/getBannerById/" + id,
                success: function (data) {
                    $('#editBannerTitle').val(data.data.bannerTitle);
                    $('#uploader').prop('src', nginxUrl + data.data.picture);
                    $('#upPicture').val(data.data.picture);
                    $('#idFlag').val(data.data.id)
                },
                error: function (msg) {
                    alert('失败')
                }
            })
            $('.wj-page').hide();
            $('#pageAddOrEdit').show();
        });

        $('#pageAddOrEdit .wj-back, #pageAddOrEdit .banner-cancel').on('click', function () {
            $('.wj-page').hide();
            $('#pageList').show();
        });
    }

    function initUpload() {
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
}());
