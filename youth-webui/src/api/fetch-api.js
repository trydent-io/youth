import axios from 'axios'

/*
 class FetchApi {
 constructor (http) {
 this.http = http
 this.baseUrl = 'http://localhost:8080'
 }

 path = url => `${this.baseUrl}/${url}`

 resolve = (method, apply, exceptionally) => method.then(res => apply(res.data)).catch(exceptionally)

 }
 */

const FetchApi = (http, baseUrl) => {
  const path = url => `${baseUrl}/${url}`
  const resolve = (method, apply, exceptionally) => method.then(res => apply(res.data)).catch(exceptionally)

  return {
    httpGet: (url, apply, exceptionally) => resolve(http.get(path(url)), apply, exceptionally),

    httpPost: (url, payload, apply, exceptionally) => resolve(http.post(path(url), payload), apply, exceptionally),

    httpPut: (url, payload, apply, exceptionally) => resolve(http.put(path(url), payload), apply, exceptionally),

    httpDelete: (url, apply, exceptionally) => resolve(http.delete(path(url)), apply, exceptionally)
  }
}

export default new FetchApi(axios, 'http://localhost:8080')
