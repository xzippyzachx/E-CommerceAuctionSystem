
if(token != null)
{
    console.log("Token: " + token);
    document.cookie="access_token="+token;

    window.location.href = `http://localhost:8080/auctions`
}
