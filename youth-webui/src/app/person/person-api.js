import fetchApi from '../../api/fetch-api'

class PersonApi {
  constructor (api) {
    this.api = api
    this.baseUrl = 'person'
  }

  path = url => `${this.baseUrl}/${url}`

  fetchAll = (apply, exceptionally) => this.api.httpGet(this.path('fetch/all'), apply, exceptionally)

  fetchOne = (uuid, apply, exceptionally) => this.api.httpGet(this.path(`${uuid}`), apply, exceptionally)

  add = (payload, apply, exceptionally) => this.api.httpPost(this.path(''), payload, apply, exceptionally)

  edit = (uuid, payload, apply, exceptionally) => this.api.httpPut(this.path(`${uuid}`), payload, apply, exceptionally)

  remove = (uuid, apply, exceptionally) => this.api.httpDelete(this.path(`${uuid}`), apply, exceptionally)
}

export default new PersonApi(fetchApi)
