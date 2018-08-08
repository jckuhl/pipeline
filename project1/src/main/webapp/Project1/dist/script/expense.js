//import { format } from '../../node_modules/date-fns/format/index.js'

export default class Expense {
    /**
     * 
     * @param {string} provider 
     * @param {string} date 
     * @param {number} amount 
     * @param {number} approval //1 - approved, 2 - pending, 3 - denied
     * @param {Employee} manager 
     * @param {Employee} owner 
     */
    constructor(eid, provider, reason, date, amount, approval, manager, owner) {
        this.eid = eid;
        this.provider = provider;
        this.reason = reason;
        this.date = date;
        this.amount = amount;
        this.approvingManager = manager
        this.requestor = owner;
        switch(approval) {
            case "APPROVED":
                this.approval = 1;
                break;
            case "PENDING":
                this.approval = 2;
                break;
            case "DENIED":
                this.approval = 3;
                break;
        }
    }
}