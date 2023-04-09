
if(token != null)
{
    let expires = new Date();
    let expireTime = expires.getTime() + 1000 * 60 * 60 * 24; // 24 Hours
    expires.setTime(expireTime);
    document.cookie=`access_token=${token};expires='${expires.toUTCString()}';`;

    window.location.href = `${window.location.protocol}//${window.location.host}/auctions`
}
