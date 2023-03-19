
const tableElement = document.getElementById("auctions-table");

const UpdateAuctions = (() => {
    for (let i = 0; i < tableElement.children.length; i++) {
        let tableChild = tableElement.children[i];
        let countdownChild = tableChild.querySelector(".auction-countdown");
        let buttonChild = tableChild.querySelector(".auction-btn");
        let auc_id = Number.parseInt(tableChild.id.slice(7));

        if(countdownChild != null && countdownChild.innerHTML !== "")
            CountDown(countdownChild.innerHTML, countdownChild);

        if(buttonChild != null) {
            buttonChild.addEventListener("click", () => {
                window.location.href = `http://localhost:8080/bidding?auc_id=${auc_id}`
            })
        }
    }
});

UpdateAuctions();