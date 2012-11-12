/**
 * Created with IntelliJ IDEA.
 * User: Jason Recillo
 * Date: 02/11/12
 * Time: 2:56 AM
 * To change this template use File | Settings | File Templates.
 */

function relativeDateString(date) {
    date = new Date(Date.parse(date));
    var delta = parseInt((new Date().getTime() - date.getTime()) / 1000);
    var diff = 0;
    if (delta < 0) {
        return 'in the future';
    } else if (delta < 45) {
        return delta + ' ' + ((delta !== 1) ? 'seconds' : 'second') + ' ago';
    } else if (delta < 60) {
        return 'less than a minute ago';
    } else if (delta < 120) {
        return 'about a minute ago';
    } else if (delta < (45 * 60)) {
        diff = Math.floor(delta / 60);
        return diff + ' ' + ((diff !== 1) ? 'minutes' : 'minute') + ' ago';
    } else if (delta < (90 * 60)) {
        return 'about an hour ago';
    } else if (delta < (24 * 60 * 60)) {
        diff = Math.floor(delta / 3600);
        return diff + ' ' + ((diff !== 1) ? 'hours' : 'hour') + ' ago';
    } else if (delta < (48 * 60 * 60)) {
        return '1 day ago';
    } else {
        diff = Math.floor(delta / 86400);
        return diff + ' ' + ((diff !== 1) ? 'days' : 'day') + ' ago';
    }
}
