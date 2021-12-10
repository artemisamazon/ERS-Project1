// Check to see if the user is logged in or not, and if not, relocate them back to
// login screen
window.addEventListener('load', async () => {

    let res = await fetch('http://localhost:8080/checkloginstatus', {
        credentials: 'include',
        method: 'GET'
    });

    if (res.status === 200) {
        let userObj = await res.json();

        if (userObj.userRole === 'employee') {
            window.location.href = 'employee-homepage.html';
        }
    } else if (res.status === 401) {
        window.location.href = 'index.html';
    }

    // If we make it past the authorization checks, call another function that will 
    // retrieve all reimtickets
    populateTableWithReimTickets();

});

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

        let td1 = document.createElement('td'); //reimticket Id appears to be switching with author Id.
        td1.innerHTML = reimticket.reimticketId; 

        let td2 = document.createElement('td');
        td2.innerHTML = reimticket.reimticketType;

        let td3 = document.createElement('td'); //find out why reimticketId and reimauthorId are switching...
        td3.innerHTML = reimticket.reimauthorId;

        let td4 = document.createElement('td'); 
        td4.innerHTML = reimticket.resolverId;

        let td5 = document.createElement('td'); 
        td5.innerHTML = reimticket.reimstatus;

        let td6 = document.createElement('td');
        td6.innerHTML = reimticket.reimamount;
       
        let td7 = document.createElement('td');
        td7.innerHTML = reimticket.reimInfo;
       

     // If the reimticket has already been reimubursed (already has reimamount), display the reimamount and resolverId
        if (reimticket.resolverId != 0) {
            td4.innerHTML = reimticket.resolverId;
          
            
           
        } else { // otherwise, display the below content
            td4.innerHTML = 'N/A';
            td5.innerHTML = 'Pending Status';
            
            
        }
            // Main challenge here is linking each button with a particular parameter
            // (reimticket id that we want to change the reimamount of)
            let reimamountInput = document.createElement('input');
            reimamountInput.setAttribute('type', 'text');

            let reimamountButton = document.createElement('button');
            reimamountButton.innerText = 'Submit Status';
           td6.appendChild(reimamountButton); 
            td6.appendChild(reimamountInput);
            
            reimamountButton.addEventListener('click', async () => {
               
                let res = await fetch(`http://localhost:8080/reimtickets/${reimticket.reimticketId}/reimstatus`, 
                    {
                        credentials: 'include',
                        method: 'PATCH',
                        body: JSON.stringify({
                            reimStatus: reimamountInput.value
                        })
                    });

                if (res.status === 200) {
                    populateTableWithReimTickets(); // Calling the function to repopulate the entire
                    // table again
                }
            });

        
        
    

        

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
      

        tbodyElement.appendChild(tr);
        }
    }




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