var nPage = 1;
var tPage = 1;
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
        getNArticle: function () {
            pageLogic.fn.getNAjax();
        },
        getTArticle: function () {
            pageLogic.fn.getTAjax();
        },
        getHArticle: function () {
            pageLogic.fn.getHAjax();
        }
    },
    event: function () {
        $('#next-n').on('click', function () {
            nPage = nPage + 1;
            pageLogic.fn.getNAjax();
        })
        $('#prev-n').on('click', function () {
            nPage = nPage - 1;
            if (nPage < 1) {
                nPage = 1
            }
            pageLogic.fn.getNAjax();
        })

        $('#next-t').on('click', function () {
            tPage = tPage + 1;
            pageLogic.fn.getTAjax();
        })
        $('#prev-t').on('click', function () {
            tPage = tPage - 1;
            if (tPage < 1) {
                tPage = 1
            }
            pageLogic.fn.getTAjax();
        })

    },
    fn: {
        getNAjax: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getArticleForSubject?id=14&coefficient=3&page=" + nPage,
                success: function (data) {
                    $('#n-item').empty();
                    if (data.data.length == 0) {
                        nPage = 1;
                        $.ajax({
                            contentType: "application/json;charset=UTF-8",
                            type: "GET",
                            dataType: "json",
                            url: "/unique/getArticleForSubject?id=14&coefficient=3&page=" + nPage,
                            success: function (data) {
                                for (var i in data.data) {
                                    $('#n-item').append('<a href="/article/' + data.data[i].id + '" class="column-background column-item">\n' +
                                        '                                            <div class="column-item-date">' + data.data[i].createTime + '</div>\n' +
                                        '                                            <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                        '                                            <div class="column-item-describe">' + data.data[i].articleBrief + '</div>\n' +
                                        '                                        </a>')
                                }
                                $('.column-background').mouseover(function () {
                                    $(this).css("background", "#E2E2E2");
                                });
                                $('.column-background').mouseout(function () {
                                    $(this).css("background", "white");
                                });
                            },
                            error: function (msg) {
                                alert('获取数据失败')
                            }
                        })
                    } else {
                        for (var i in data.data) {
                            $('#n-item').append('<a href="/article/' + data.data[i].id + '" class="column-background column-item">\n' +
                                '                                            <div class="column-item-date">' + data.data[i].createTime + '</div>\n' +
                                '                                            <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                '                                            <div class="column-item-describe">' + data.data[i].articleBrief + '</div>\n' +
                                '                                        </a>')
                        }
                        $('.column-background').mouseover(function () {
                            $(this).css("background", "#E2E2E2");
                        });
                        $('.column-background').mouseout(function () {
                            $(this).css("background", "white");
                        });
                    }
                },
                error: function (msg) {
                    alert('获取数据失败')
                }
            })
        },
        getTAjax: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getArticleForSubject?id=15&coefficient=4&page=" + tPage,
                success: function (data) {
                    $('#t-item').empty();
                    if (data.data.length == 0) {
                        tPage = 1;
                        $.ajax({
                            contentType: "application/json;charset=UTF-8",
                            type: "GET",
                            dataType: "json",
                            url: "/unique/getArticleForSubject?id=15&coefficient=4&page=" + tPage,
                            success: function (data) {
                                for (var i in data.data) {
                                    $('#t-item').append('                    <div class="column-item">\n' +
                                        '                                        <a href="/article/' + data.data[i].id + '" class="column-item-inner">\n' +
                                        '                                            <div class="column-div">\n' +
                                        '                                                <div class="column-item-divider"></div>\n' +
                                        '                                                <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                        '                                            </div>\n' +
                                        '                                            <img draggable="false" class="column-item-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                                        '                                        </a>\n' +
                                        '                                    </div>')
                                }
                            },
                            error: function (msg) {
                                alert('获取数据失败')
                            }
                        })
                    } else {
                        for (var i in data.data) {
                            $('#t-item').append('                    <div class="column-item">\n' +
                                '                                        <a href="/article/' + data.data[i].id + '" class="column-item-inner">\n' +
                                '                                            <div class="column-div">\n' +
                                '                                                <div class="column-item-divider"></div>\n' +
                                '                                                <div class="column-item-title">' + data.data[i].articleTitle + '</div>\n' +
                                '                                            </div>\n' +
                                '                                            <img draggable="false" class="column-item-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                                '                                        </a>\n' +
                                '                                    </div>')
                        }
                    }
                },
                error: function (msg) {
                    alert('获取数据失败')
                }
            })
        },
        getHAjax: function () {
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/unique/getArticleForSubject?id=16&coefficient=4&page=1",
                success: function (data) {
                    $('#h-item').empty();
                    for (var i in data.data) {
                        $('#h-item').append('                    <a href="javascript:;" class="column-item">\n' +
                            '                                        <img draggable="false" class="column-image" src="' + nginxUrl + data.data[i].picture + '">\n' +
                            '                                    </a>')
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
    pageLogic.init.getNArticle();
    pageLogic.init.getTArticle();
    pageLogic.init.getHArticle();
    pageLogic.event();
})