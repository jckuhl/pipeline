import Employee from './employee.js';
import Expense from './expense.js';
import Connection from './connection.js';
import cleanString from './cleanser.js';
/**
 * Controls the contents of the #app div
 */
new Vue({

    el: '#app',
    data: {
        //Employee
        employee: null,
        tempEmployee: null,
        editEmployee: {
            fname: '',
            lname: '',
            position: '',
            street: '',
            city: '',
            state: '',
            zip: '',
            phone: '',
            email: ''
        },

        //Employees
        employees: [],
        emplResponse: [],
        selectedEmployee: null,
        isEmployeeLoaded: false,

        //Expenses
        expenses:[],
        files: [],
        employeeFilter: 'all',
        approvalFilter: 'all',
        loadedExpenses: true,

        //app management
        viewingAs: 'employee',
        viewing: 'profile',
        role: 'Employee',
        editMode: false,

        //ERS Add worksheet
        newExpenses: [
            {
                provider: '',
                date: '',
                reason: '',
                amount: 0
            }
        ],

        //Create Employee
        newEmployee: {
            fname: '',
            lname: '',
            employeeId: '',
            position: '',
            street: '',
            city: '',
            state: '',
            zip: '',
            phone: '',
            email: '',
        },
        generatedId: false
    },
    computed: {
        /**
         * Allows a manager to filter the expenses table
         */
        filteredRequestList() {
            const fullname = (employee)=> employee.fname + " " + employee.lname;
            if(this.employeeFilter === 'all' && this.approvalFilter === 'all') {
                return this.expenses;
            }
            let filteredList = [];
            if(this.employeeFilter !== 'all' && this.approvalFilter === 'all') {
                //employeeId is unique and therefore the returned array should always be exactly one element
                const employee = this.employees
                                    .filter(emp=> emp.employeeId === this.employeeFilter)[0].fullname;
                filteredList = this.expenses.filter(expense=> fullname(expense.requestor) === employee);
                return filteredList;
            }
            if(this.employeeFilter === 'all' && this.approvalFilter !== 'all') {
                filteredList = this.expenses
                                    .filter(expense=> expense.approval == this.approvalFilter);
                return filteredList;
            }
            if(this.employeeFilter !== 'all' && this.approvalFilter !== 'all') {
                const employee = this.employees
                                    .filter(emp=> emp.employeeId === this.employeeFilter)[0].fullname;
                filteredList = this.expenses
                                    .filter(expense=> fullname(expense.requestor) === employee)
                                    .filter(expense=> expense.approval == this.approvalFilter);
                return filteredList;
            }
        },
    },
    watch: {
        /**
         * Sets all the values, on load, of the edit employee object to the employee object
         * so the profile page looks and functions appropriately
         */
        isEmployeeLoaded: function() {
            for(let member in this.employee) {
                if(this.editEmployee.hasOwnProperty(member)) {
                    this.editEmployee[member] = this.employee[member];
                }
            }
        }
    },
    filters: {
        capitalize(str) {
            if(!str) return '';
            else return str.slice(0,1).toUpperCase() + str.slice(1);
        }
    },
    methods: {
        getPositionTitle(position) {
            switch(position) {
                case "MANAGER":
                    return "Regional Manager";
                case "ASSISTANT":
                    return "Assistant to the Regional Manager"
                case "SALES":
                    return "Sales";
                case "RECEPTION":
                    return "Reception";
                case "ACCOUNTING":
                    return "Accounting";
                case "HR":
                    return "Human Resources";
                case "CS":
                    return "Customer Service";
                case "QA":
                    return "Quality Assurance";
            }
        },
        async switchProfile(index) {
            const options = [
                'profile',
                'new',
                'expenses',
                'employees',
                'register'
            ]
            this.viewing = options[index];
            if(this.viewing == 'expenses') {
                this.loadedExpenses = false;
                let expenseData = await Connection.get('/project1/getExpenses.do', { method: "POST"});
                if(expenseData) {
                    this.loadedExpenses = true;
                    console.log(expenseData);
                    this.expenses = expenseData.map(expense=> {
                        return new Expense(
                            expense.eid,
                            expense.provider,
                            expense.reason,
                            expense.date,
                            expense.amount,
                            expense.approval,
                            expense.approvingManager,
                            expense.owner
                        );
                    });
                }
            }
            if(this.viewing == 'employees') {
                let emplData = await Connection.get('/project1/getEmployees.do', { method: "POST"});
                if(emplData) {
                    console.log(emplData);
                    this.employees = emplData.map(empl=> {
                        return new Employee(
                            empl.fname,
                            empl.lname,
                            empl.employeeId,
                            empl.position,
                            empl.street,
                            empl.city,
                            empl.state,
                            empl.zip,
                            empl.phone,
                            empl.email,
                            empl.isManager
                        );
                    });
                }
            }
        },
        /**
         * Switches between being able to edit the employee profile or not
         */
        toggleEditMode() {
            event.preventDefault();
            this.editMode = !this.editMode;
        },
        /**
         * Switches betweene employee and manager mode
         */
        toggleRole() {
            if(this.role == 'Manager') {
                this.role = 'Employee';
                this.employee = this.tempEmployee;
                this.editEmployee = this.employee;
            } else {
                this.role = 'Manager';
            }
        },
        /**
         * Adds another expense to the new expenses table
         */
        addRow() {
            event.preventDefault();
            this.newExpenses.push({
                provider: '',
                date: '',
                reason: '',
                amount: 0
            });
        },
        deleteRow() {
            event.preventDefault();
            if(this.newExpenses.length > 1) this.newExpenses.pop();
        },
        async generateId() {
            event.preventDefault();
            const idString = await Connection.get('/project1/getEmployeeId.do', { method: 'POST' });
            if(idString) {
                this.newEmployee.employeeId = idString;
                this.generatedId = true;
            }
        },
        async createNewEmployee() {
            event.preventDefault();
            const newEmployee = new Employee(
                cleanString(this.newEmployee.fname),
                cleanString(this.newEmployee.lname),
                cleanString(this.newEmployee.employeeId),
                cleanString(this.newEmployee.position),
                cleanString(this.newEmployee.street),
                cleanString(this.newEmployee.city),
                cleanString(this.newEmployee.state),
                cleanString(this.newEmployee.zip),
                cleanString(this.newEmployee.phone),
                this.newEmployee.email,
                false
            );
            Connection.send('/project1/create.do', { 
                    method: 'POST', 
                    body: JSON.stringify(newEmployee),
                    headers: {
                        "Content-Type": "application/json; charset=utf-8"
                    }
            });
            this.generatedId = false;
            this.clearObject(this.newEmployee);
        },
        clearObject(object) {
            for(let member in object) object[member] = '';
        },
        submitProfileChanges() {
            event.preventDefault();
            for(let member in this.editEmployee) {
                this.employee[member] = this.editEmployee[member];
            }
            this.editMode = false;
            Connection.send('/project1/editemployee.do', {
                    method: 'POST',
                    body: JSON.stringify(this.employee),
                    headers: {
                        "Content-Type": "application/json; charset=utf-8"
                    }
            });
        },
        submitNewExpenses() {
            event.preventDefault();
            const expenses = [];
            for(let expense of this.newExpenses) {
                for(let member in expense) {
                    console.log(expense);
                    if(expenses[member] === '' || expenses[member] === 0) {
                        console.log('Please fill out all fields');
                        return;
                    }
                }
            }
            this.newExpenses.forEach(expense=> {
                expenses.push(new Expense(0,
                    cleanString(expense.provider),
                    cleanString(expense.reason),
                    cleanString(expense.date),
                    expense.amount,
                    2,
                    null,
                    this.employee.employeeId
                ));
            });
            Connection.send('/project1/submitExpenses.do', {
                method: 'POST', 
                body: JSON.stringify(expenses),
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                }
            });
            this.newExpenses = [
                {
                    provider: '',
                    reason: '',
                    date: '',
                    amount: 0,
                }
            ];
            this.files = [];
        },
        selectEmployee(employee) {
            this.employee = employee;
            this.editEmployee = employee;
            this.employee.isManager = true; //only a manager can get to the screen that calls this method
        },
        getFiles() {
            const files = event.target.files;
            for(let file of files) {
                this.files.push(file);
            }
            console.log(this.files);
        },
        resolveExpense(expense) {
            expense.approvingManager = this.tempEmployee.fullname;
            Connection.send('/project1/resolveExpense.do', {
                method: 'POST', 
                body: JSON.stringify(expense),
                headers: {
                    "Content-Type": "application/json; charset=utf-8"
                }
            });
        },
        async logout() {
            const redirectURL = await Connection.get('/project1/logout.do');
            if(redirectURL) {
                window.location.assign(redirectURL.redirect);
            }
        }
    },
    /**
     * This is where we reach out to Oracle to grab info
     * For now, we'll use myjson to fake it.
     */
    async mounted() {
        let emplData = await Connection.get('/project1/getEmployees.do', { method: "POST"});
        console.log(emplData);
        if(emplData) {
            this.employees = emplData.map(empl=> {
                return new Employee(
                    empl.fname,
                    empl.lname,
                    empl.employeeId,
                    empl.position,
                    empl.street,
                    empl.city,
                    empl.state,
                    empl.zip,
                    empl.phone,
                    empl.email,
                    empl.isManager
                );
            });
        }
        let currentEmployee = await Connection.get(
            '/project1/getCurrentEmployee.do', 
            { 
                method: "POST"
        });
        if(currentEmployee) {
            console.log(currentEmployee);
            if(currentEmployee.message != undefined && currentEmployee.message == 'error') {
                window.location.assign('../html/error.html');
                return;
            }
            this.employee = new Employee(
                currentEmployee.fname,
                currentEmployee.lname,
                currentEmployee.employeeId,
                currentEmployee.position,
                currentEmployee.street,
                currentEmployee.city,
                currentEmployee.state,
                currentEmployee.zip,
                currentEmployee.phone,
                currentEmployee.email,
                currentEmployee.isManager
            );
            this.tempEmployee = this.employee;
            this.employeeFilter = this.employee.employeeId;
            this.isEmployeeLoaded = true;
        }
        let expenseData = await Connection.get('/project1/getExpenses.do', { method: "POST"});
        console.log(expenseData);
        if(expenseData) {
            this.expenses = expenseData.map(expense=> {
                return new Expense(
                    expense.eid,
                    expense.provider,
                    expense.reason,
                    expense.date,
                    expense.amount,
                    expense.approval,
                    expense.approvingManager,
                    expense.owner
                );
            });
        }

    }

});