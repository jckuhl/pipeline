const cleanString = (string)=> string.toString().replace(/[^a-zA-Z0-9\s]+/g,'');

export default cleanString;