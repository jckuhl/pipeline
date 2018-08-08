const Connection = {
    get: async (url, params={})=> (await fetch(url, params)).json(),
    send: async (url, params={})=> (await fetch(url, params)),
}

export default Connection;