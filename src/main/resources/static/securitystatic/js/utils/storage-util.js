(function (window) {
    function saveUserToLocal(userName) {
        window.localStorage.setItem('userName', userName);
    }

    function getUserFromLocal() {
        return window.localStorage.getItem('userName');
    }

    function removeUserFromLocal() {
        window.localStorage.removeItem('userName');
    }

    window.StorageUtil = {saveUserToLocal, getUserFromLocal, removeUserFromLocal};
}(window));
