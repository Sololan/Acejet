var vePage = 1;
var lPage = 1;
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
                },
                error: function (msg) {
                    alert('获取nginx地址失败')
                }
            })
        },
        getVeArticle: function () {
            pageLogic.fn.getVeAjax();
        },
        getLArticle: function () {
            pageLogic.fn.getLAjax();
        }
    },
    event: function () {
        $('#next-ve').on('click', function () {
            vePage = vePage + 1;
            pageLogic.fn.getVeAjax();
        })
        $('#prev-ve').on('click', function () {
            vePage = vePage - 1;
            if (vePage < 1) {
                vePage = 1
            }
            pageLogic.fn.getVeAjax();
        })
        $('#next-l').on('click', function () {
            lPage = lPage + 1;
            pageLogic.fn.getLAjax();
        })
        $('#prev-l').on('click', function () {
            lPage = lPage - 1;
            if (lPage < 1) {
                lPage = 1
            }
            pageLogic.fn.getLAjax();
        })
    },
    fn: {
        getVeAjax: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getArticleForSubject?id=89&coefficient=6&page=" + vePage,
                success: function (data) {
                    $('#ve-item').empty();
                    if (data.data.length == 0) {
                        vePage = 1;
                        $.ajax({
                            contentType: "application/json;charset=UTF-8",
                            type: "GET",
                            dataType: "json",
                            url: "/unique/getArticleForSubject?id=89&coefficient=6&page=" + vePage,
                            success: function (data) {
                                for (var i in data.data) {
                                    $('#ve-item').append('<div class="column-item">\n' +
                                        '                                        <div class="column-item-image">\n' +
                                        '                                            <img draggable="false" class="column-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                                        '                                        </div>\n' +
                                        '                                        <div class="column-item-footer">\n' +
                                        '                                            <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                        '                                            <div class="column-item-sub-title">' + data.data[i].articleSubtitle + '</div>\n' +
                                        '                                        </div>\n' +
                                        '                                    </div>')
                                }
                            },
                            error: function (msg) {
                                alert('获取数据失败')
                            }
                        })
                    }
                    for (var i in data.data) {
                        $('#ve-item').append('<div class="column-item">\n' +
                            '                                        <div class="column-item-image">\n' +
                            '                                            <img draggable="false" class="column-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                            '                                        </div>\n' +
                            '                                        <div class="column-item-footer">\n' +
                            '                                            <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                            '                                            <div class="column-item-sub-title">' + data.data[i].articleSubtitle + '</div>\n' +
                            '                                        </div>\n' +
                            '                                    </div>')
                    }
                },
                error: function (msg) {
                    alert('获取数据失败')
                }
            })
        },
        getLAjax: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getArticleForSubject?id=92&coefficient=4&page=" + lPage,
                success: function (data) {
                    $('#l-item').empty();
                    if (data.data.length == 0) {
                        lPage = 1;
                        $.ajax({
                            contentType: "application/json;charset=UTF-8",
                            type: "GET",
                            dataType: "json",
                            url: "/unique/getArticleForSubject?id=92&coefficient=4&page=" + lPage,
                            success: function (data) {
                                for (var i in data.data) {
                                    $('#l-item').append('                   <div class="column-item">\n' +
                                        '                                        <div class="column-item-image">\n' +
                                        '                                            <img draggable="false" class="column-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                                        '                                        </div>\n' +
                                        '                                        <div class="column-item-describe">\n' +
                                        '                                            <div class="column-item-describe-inner">' + data.data[i].articleSubtitle + '</div>\n' +
                                        '                                        </div>\n' +
                                        '                                        <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                        '                                    </div>')
                                }
                            },
                            error: function (msg) {
                                alert('获取数据失败')
                            }
                        })
                    }
                    for (var i in data.data) {
                        $('#l-item').append('                   <div class="column-item">\n' +
                            '                                        <div class="column-item-image">\n' +
                            '                                            <img draggable="false" class="column-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                            '                                        </div>\n' +
                            '                                        <div class="column-item-describe">\n' +
                            '                                            <div class="column-item-describe-inner">' + data.data[i].articleSubtitle + '</div>\n' +
                            '                                        </div>\n' +
                            '                                        <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                            '                                    </div>')
                    }
                },
                error: function (msg) {
                    alert('获取数据失败')
                }
            })
        }
    }

}

$(function () {
    pageLogic.init.getNginxUrl();
    pageLogic.init.getVeArticle();
    pageLogic.init.getLArticle();
    pageLogic.event();
})