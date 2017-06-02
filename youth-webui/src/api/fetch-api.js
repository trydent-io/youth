import axios from 'axios'

class FetchApi {
  constructor (http) {
    this.http = http
    this.baseUrl = 'http://localhost:8080'
  }

  path = url => `${this.baseUrl}/${url}`

  resolve = (method, apply, exceptionally) => method.then(res => apply(res.data)).catch(exceptionally)

  httpGet = (url, apply, exceptionally) => this.resolve(this.http.get(this.path(url)), apply, exceptionally)

  httpPost = (url, payload, apply, exceptionally) => this.resolve(this.http.post(this.path(url), payload), apply, exceptionally)

  httpPut = (url, payload, apply, exceptionally) => this.resolve(this.http.put(this.path(url), payload), apply, exceptionally)

  httpDelete = (url, apply, exceptionally) => this.resolve(this.http.delete(this.path(url)), apply, exceptionally)
}

export default new FetchApi(axios)
