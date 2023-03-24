
const formElement = document.getElementById("card-form");
const expeditedInput = document.getElementById("expedited-input");
const cardNumberInput = document.getElementById("card-number-input");
const cardHolderInput = document.getElementById("card-holder-name-input");
const cardExpiryInput = document.getElementById("card-expiry-input");
const cardCodeInput = document.getElementById("card-code-input");

const totalCostElement = document.getElementById("total-cost");

let totalCost = auc_current_price + itm_shipping_cost;

expeditedInput.addEventListener("change", () => {
    if(expeditedInput.checked === true)
        totalCost = auc_current_price + itm_shipping_cost + itm_expedited_cost;
    else
        totalCost = auc_current_price + itm_shipping_cost;

    totalCostElement.innerHTML = `Total Cost: $${totalCost}`;
});

const SubmitButton = (() => {

    if(!validateForm())
        return;

    let Http = new XMLHttpRequest();
    let url = 'http://localhost:8080/api/new-payment';
    Http.open("POST", url);
    Http.setRequestHeader('Content-type', 'application/json; charset=UTF-8');

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
                window.location.href = `http://localhost:8080/receipt?auc_id=${auc_id}`
            }
        }
    }
});

validateForm = (() => {
    return formElement.reportValidity();
});