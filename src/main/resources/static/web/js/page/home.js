var nginxUrl
window.pageLogic = {
    init: {
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
                    window.pageLogic.init.initBanner()
                },
                error: function (msg) {
                    alert('获取nginx地址失败')
                }
            })
        },
        initBanner: function () {
            $('#banner-item').empty()
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/banner/getAllOpenBannerS",
                success: function (data) {
                    $('#banner-item').empty();
                    for (var i in data.data) {
                        $('#banner-item').append('<img class="column-image" banner-id="' + data.data[i].id + '"src="' + nginxUrl + data.data[i].picture + '">')
                    }
                    $('#banner-text').text(data.data[0].bannerTitle)
                    pageLogic.fn.renderHomeCarousel()

                },
                error: function (msg) {
                    alert('失败')
                }
            })
        }
    },
    event: function () {

    },
    fn: {
        renderHomeCarousel: function () {
            layui.carousel.render({
                // 指向容器选择器，如：elem: '#id'。也可以是DOM对象
                elem: '#carousel',
                // 设定轮播容器宽度，支持像素和百分比
                width: '100%',
                // width: '19.03rem',
                // 设定轮播容器高度，支持像素和百分比
                height: '7.1rem',
                // 切换箭头默认显示状态，可选值为：hover（悬停显示）always（始终显示）none（始终不显示）
                arrow: 'none'
            });
            layui.carousel.on('change', function (obj) {
                var id = $(obj.item).attr('banner-id');
                $.ajax({
                    contentType: "application/json;charset=UTF-8",
                    type: "GET",
                    dataType: "json",
                    url: "/banner/getBannerById/" + id,
                    success: function (data) {
                        $('#banner-text').empty();
                        $('#banner-text').text(data.data.bannerTitle)
                    },
                    error: function (msg) {
                        alert('失败')
                    }
                })
                console.log(obj);
            })
        }
    }

}

$(function () {
    pageLogic.init.getNginxUrl();
    // pageLogic.init.initBanner();
})