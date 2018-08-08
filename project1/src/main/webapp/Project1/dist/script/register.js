import Connection from './connection.js';

new Vue({
    el: '#setLogin',
    data: {
        employeeFound: false,
        employees: [],
        fname: '',
        lname: '',
        employeeId: '',
        attempts: 0,
        usernameAttempts: 0,
        username: '',
        password: '',
        passwordCheck: '',
        passwordValidated: false,
        passwordConfirmed: false,
        usernameValidated: false
    },
    computed: {
        getFullName() {
            const title = word=> word.slice(0,1).toUpperCase() + word.slice(1); 
            return title(this.fname) + ' ' + title(this.lname);
        }
    },
    watch: {
        passwordCheck: function() {
            this.passwordConfirmed = this.passwordCheck === this.password
        }
    },
    methods: {
        async findEmployee() {
            event.preventDefault();
            this.employees = await Connection.get('/project1/getEmployees.do', { method: "POST"});
            this.employeeFound = this.employees.filter(empl=> { 
                return empl.fname.toLowerCase().trim() == this.fname.toLowerCase().trim() && 
                    empl.lname.toLowerCase().trim() == this.lname.toLowerCase().trim() && 
                    empl.employeeId.toLowerCase().trim() == this.employeeId.toLowerCase().trim()
            }).length != 0;
            this.attempts += 1;
        },
        async validateUsername() {
            this.usernameAttempts += 1;
            const usernameTaken = await Connection.get('/project1/validateUsername.do', {
                method: 'POST', 
                body: JSON.stringify(this.username),
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                }
            });
            this.usernameValidated = this.username.length > 0 && usernameTaken;
        },
        validatePassword() {
            let numbers = 0;
            let specialChars = 0;
            this.password.split('').forEach(char => {
                if('01234567890'.includes(char)) numbers += 1;
                if('!@#$%^&*()_+-={}[]|:";\'<>?,./`~'.includes(char)) specialChars += 1;
            });
            this.passwordValidated = numbers >= 2 &&  specialChars >= 2 && this.password.length >= 8;
        }
    }
});