// Initial page load
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'finmanager') {
            window.location.href = 'finmanager-homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    populateTableWithReimTickets();
});

// Logout btn
let logoutBtn = document.querySelector('#logout');

logoutBtn.addEventListener('click', async () => {

    let res = await fetch('http://localhost:8080/logout', {
        'method': 'POST',
        'credentials': 'include'
    });

    if (res.status === 200) {
        window.location.href = 'index.html';
    }

})


async function populateTableWithReimTickets() {
    let res = await fetch('http://localhost:8080/reimtickets', {
        credentials: 'include',
        method: 'GET'
    });

    let tbodyElement = document.querySelector("#reimticket-table tbody");
    tbodyElement.innerHTML = '';
    let reimticketArray =  await res.json();

    for (let i = 0; i < reimticketArray.length; i++) {
        let reimticket = reimticketArray[i];

        let tr = document.createElement('tr');

        let td1 = document.createElement('td'); //retrieves employee ID (reimauthorId)
        td1.innerHTML = reimticket.reimauthorId;

        let td2 = document.createElement('td');
        td2.innerHTML = reimticket.reimticketType;


        let td3 = document.createElement('td'); 
        let td4 = document.createElement('td'); 

        if (reimticket.resolverId != 0) {
            td3.innerHTML = reimticket.reimstatus;
            td4.innerHTML = reimticket.resolverId;
        } else {
            td3.innerHTML = 'Pending';
            td4.innerHTML = 'Pending Review';
        }
 

        let td5 = document.createElement('td');
        td5.innerHTML = reimticket.reimticketId; // not retrieving correct id for reimticketId

        let td6 = document.createElement('td');
        td6.innerHTML = reimticket.reimInfo;

        let td7 = document.createElement('td');
        td7.innerHTML = reimticket.reimamount;

        let td8 = document.createElement('td');
        let viewImageButton = document.createElement('button');
        viewImageButton.innerHTML = 'View Image';
        td8.appendChild(viewImageButton);

        viewImageButton.addEventListener('click', async () => {
            // Add the is-active class to the modal (so that the modal appears)
            // inside of the modal on div.modal-content (div w/ class modal-content)
            //  -> img tag with src="http://localhost:8080/reimtickets/{id}/image"
            let reimticketImageModal = document.querySelector('#reimticket-image-modal');

            // Close button
            let modalCloseElement = reimticketImageModal.querySelector('button');
            modalCloseElement.addEventListener('click', () => {
                reimticketImageModal.classList.remove('is-active');
            });

            // you can take an element and use querySelector on it to find the child elements
            // that are nested within it
            let modalContentElement = reimticketImageModal.querySelector('.modal-content');
            modalContentElement.innerHTML = '';

            let imageElement = document.createElement('img');
            imageElement.setAttribute('src', `http://localhost:8080/reimtickets/${reimticket.reimTicketId}/image`);
            modalContentElement.appendChild(imageElement);

            reimticketImageModal.classList.add('is-active'); // add a class to the modal element to have it display
        });

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);

        tbodyElement.appendChild(tr);
    }
}

// Submitting reimticket
let reimticketSubmitButton = document.querySelector('#submit-reimticket-btn');

reimticketSubmitButton.addEventListener('click', submitReimTicket);

async function submitReimTicket() {

    let reimticketTypeInput = document.querySelector('#reimticket-type');
    let reimticketImageInput = document.querySelector('#reimticket-file');
    let reimticketDescriptionInput = document.querySelector('#reimticket-description');
    let reimticketAmountInput = document.querySelector('#reimticket-amount');
    const file = reimticketImageInput.files[0];

    let formData = new FormData();
    formData.append('reimticket_type', reimticketTypeInput.value);
    formData.append('reimticket_image', file);
    formData.append('reimticket_description', reimticketDescriptionInput.value);
    formData.append('reimticket_amount', reimticketAmountInput.value);

    let res = await fetch('http://localhost:8080/reimtickets', {
        method: 'POST',
        credentials: 'include',
        body: formData
    });

    if (res.status === 201) { // If we successfully added an reimticket
        populateTableWithReimTickets(); // Refresh the table of reimtickets
    } else if (res.status === 400) {
        let reimticketForm = document.querySelector('#reimticket-submit-form');

        let data = await res.json();

        let pTag = document.createElement('p');
        pTag.innerHTML = data.message;
        pTag.style.color = 'red';

        reimticketForm.appendChild(pTag);
    }
}