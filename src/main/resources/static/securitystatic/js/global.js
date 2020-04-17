(function () {
    /**
     * 在文档加载后激活函数
     */
    $(document).ready(function () {
        registerEventHandler();
        initSubjet();
    });

    // =====================================================================================================================================
    /**
     * 初始化栏目管理
     */
    function initSubjet() {
        $('#subject-list').empty();
        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: "GET",
            dataType: "json",
            url: '/subject/getParentSubjectS',
            success: function (data) {
                var query = window.location.pathname;
                for (var i in data.data) {
                    if (query.charAt(query.length - 1) == +data.data[i].id) {
                        $('#subject-list').append('<dd><a class="layui-this" href="/security/subject/' + data.data[i].id + '">' + data.data[i].subjectName + '</a></dd>')
                    } else {
                        $('#subject-list').append('<dd><a href="/security/subject/' + data.data[i].id + '">' + data.data[i].subjectName + '</a></dd>')
                    }
                }
            },
            error: function (msg) {
                layui.alert('栏目失败')
            }
        })
    }
    /**
     * 注册事件
     */
    function registerEventHandler() {
        $('.wj-logo').on('click', function () {
            window.location.href = '/security/banner';
        });

        var interval = null;
        $('.layui-side-toggle-inner').on('click', function () {
            $('.layui-layout-admin').toggleClass('layui-side-show');
            if (interval) {
                clearInterval(interval);
            }
            var timeout = 50;
            var timeTotal = 0;
            interval = setInterval(function () {
                if (timeTotal < 350) {
                    layui.table.resize();
                    timeTotal += timeout;
                }
            }, timeout);
        });

        $('.wj-search-toggle').on('click', function () {
            $(this).parents('.wj-search').toggleClass('wj-advance-on');
        });

        $('#modifyPwd').on('click', function () {
            $('#psd').val('');
            $('#repsd').val('');
            layer.open({
                title: '修改密码',
                type: 1,
                content: $('#modifyPwdModal') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            });
        });

        $('#resetPsd').on('click', function () {
            console.log($('#psd').val())
            if ($('#psd').val() != $('#repsd').val()) {
                alert("密码不一致请重新输入");
                return;
            }
            $.ajax({
                contentType: "application/json;charset=UTF-8",
                type: "GET",
                dataType: "json",
                url: "/util/setPassword/" + $('#psd').val(),
                success: function (data) {
                    alert("修改成功");
                    window.location.reload()
                },
                error: function (msg) {
                    alert("修改成功");
                    window.location.reload()
                }
            })
        });
    }
}());
