/**
 * rem适配
 */
(function (doc, win) {
    const docEl = doc.documentElement;
    const resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize';
    const reCalc = function () {
        let clientWidth = (docEl.clientWidth || 0) > 1920 ? 1920 : docEl.clientWidth;
        if (!clientWidth) return;
        docEl.style.fontSize = 100 * (clientWidth / 1920) + 'px';
        doc.body.style.visibility = 'visible'
    };
    if (!doc.addEventListener) return;
    win.addEventListener(resizeEvt, reCalc, false);
    doc.addEventListener('DOMContentLoaded', reCalc, false);
})(document, window);
