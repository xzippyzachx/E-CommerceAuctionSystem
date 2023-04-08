
const formElement = document.getElementById("card-form");
const expeditedInput = document.getElementById("expedited-input");
const cardNumberInput = document.getElementById("card-number-input");
const cardHolderInput = document.getElementById("card-holder-name-input");
const cardExpiryInput = document.getElementById("card-expiry-input");
const cardCodeInput = document.getElementById("card-code-input");

const totalCostElement = document.getElementById("total-cost");

let totalCost = auc_current_price + itm_shipping_cost;

if(expeditedInput != null) {
    expeditedInput.addEventListener("change", () => {
        if(expeditedInput.checked === true)
            totalCost = auc_current_price + itm_shipping_cost + itm_expedited_cost;
        else
            totalCost = auc_current_price + itm_shipping_cost;

        totalCostElement.innerHTML = `Total Cost: $${totalCost}`;
    });
}

const SubmitButton = (() => {

    if(!validateForm())
        return;

    let Http = new XMLHttpRequest();
    let url = `${window.location.protocol}//${window.location.host}/api/new-payment`;
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');
    Http.setRequestHeader('Authorization', 'Bearer ' + getCookie("access_token"));

    let payload = {
        auc_id: auc_id,
        pay_amount: totalCost,
        pay_card_number: cardNumberInput.value,
        pay_person_name: cardHolderInput.value,
        pay_expiry_date: cardExpiryInput.value,
        pay_security_code: cardCodeInput.value,
        expedited_shipping: expeditedInput.checked
    };

    Http.send(JSON.stringify(payload));
    Http.onreadystatechange = (e) => {
        if(Http.readyState === XMLHttpRequest.DONE) {
            if(Http.responseText) {
                window.location.href = `${window.location.protocol}//${window.location.host}/receipt?auc_id=${auc_id}`
            }
        }
    }
});

validateForm = (() => {
    return formElement.reportValidity();
});

getCookie = ((cookieName) => {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
});