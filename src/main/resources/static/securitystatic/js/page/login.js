(function () {
    /**
     * 在文档加载后激活函数
     */
    $(document).ready(function () {
        registerEventHandler();
        handleGetKeepUser();
    });

    // =====================================================================================================================================

    /**
     * 注册事件
     */
    function registerEventHandler() {
        $('#user, #pwd').on('keyup', handleInputKeyup);
        $('#login').on('click', doLoginSubmit);
    }

    /**
     * 处理文本框keyup事件
     * @param evt
     */
    function handleInputKeyup(evt) {
        hideErrMsg();
        var disabled = Boolean(!$('#user').val() || !$('#pwd').val());
        $('#login').prop('disabled', disabled);
        if (!disabled) handleEnterKeyEvent(evt);
    }

    /**
     * 处理按下回车键的事件
     * @param evt
     */
    function handleEnterKeyEvent(evt) {
        if (String(evt.keyCode) === '13') {
            doLoginSubmit();
        }
    }

    /**
     * 显示错误消息
     * @param errMsg
     */
    function showErrMsg(errMsg) {
        $('#errMsg').text(errMsg);
        $('.error-group').show();
    }

    /**
     * 隐藏错误消息
     */
    function hideErrMsg() {
        var errorGroup = $('.error-group');
        if (!errorGroup.is(':hidden')) {
            $('#errMsg').text('');
            errorGroup.hide();
        }
    }

    /**
     * 登录提交
     */
    function doLoginSubmit() {
        handleSetKeepUser(loginInfo.keepUser, loginInfo.userName);
        // var loginInfo = { userName: $('#user').val(), password: $('#pwd').val(), keepUser: $('#keepUser').is(':checked') };
        // if (loginInfo.userName === 'admin' && loginInfo.password === '1') {
        //     handleSetKeepUser(loginInfo.keepUser, loginInfo.userName);
        //     location.href = 'banner.html';
        // } else {
        //     showErrMsg('用户名或密码错误');
        // }
    }

    /**
     * 处理记住用户名设置（保存或删除）
     * @param keepUser
     * @param userName
     */
    function handleSetKeepUser(keepUser, userName) {
        if (keepUser) {
            StorageUtil.saveUserToLocal(userName);
        } else {
            StorageUtil.removeUserFromLocal();
        }
    }

    /**
     * 处理记住用户名获取（获取）
     */
    function handleGetKeepUser() {
        var userName = StorageUtil.getUserFromLocal();
        if (!!userName) {
            $('#user').val(userName);
            $('#keepUser').prop('checked', true);
        }
    }
}());


