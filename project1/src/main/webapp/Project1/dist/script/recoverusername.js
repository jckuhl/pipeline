import Connection from './connection.js'

(function() {

    const btn = document.querySelector('button');
    const fname = document.querySelector('input[name="fname"');
    const lname = document.querySelector('input[name="lname"');
    const employeeId = document.querySelector('input[name="employeeId"]');
    const email = document.querySelector('input[name="email"');
    const responseDiv = document.getElementById('response');

    btn.addEventListener('click', async (event)=> {
        event.preventDefault();
        const query = {
            fname: fname.value,
            lname: lname.value,
            employeeId: employeeId.value,
            email: email.value,
        }
        const response = await Connection.get('http://localhost:8082/project1/forgotusername.do',{
            method: 'POST', 
            body: JSON.stringify(query),
            headers: {
                "Content-Type": "application/json; charset=utf-8"
            }
        });
        console.log(response);
        if(response && response.invalid != "true") {
            responseDiv.innerHTML = '';
            const div = document.createElement('div');
            div.innerHTML = `
                <h1>Thank you!</h1>
                <p>An Email has been sent!</p>
                <p><a href="../html/index.html">Return home</a></p>`;
            responseDiv.appendChild(div);
            btn.disabled = true;
        } else {
            responseDiv.innerHTML = '';
            const div = document.createElement('div');
            div.innerHTML = `
                <h1>I'm sorry!</h1>
                <p>No one was found.  Please try again</p>
                <p><a href="../html/index.html">Return home</a></p>`;
            responseDiv.appendChild(div);
        }
    });
})();