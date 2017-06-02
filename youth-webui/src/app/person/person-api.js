import fetchApi from '../../api/fetch-api'

const PersonApi = (api, baseUrl) => {
  const path = url => `${baseUrl}/${url}`

  return {
    fetchAll: (apply, exceptionally) => api.httpGet(path('fetch/all'), apply, exceptionally),

    fetchOne: (uuid, apply, exceptionally) => api.httpGet(path(`${uuid}`), apply, exceptionally),

    add: (payload, apply, exceptionally) => api.httpPost(path(''), payload, apply, exceptionally),

    edit: (payload, apply, exceptionally) => api.httpPut(path(''), payload, apply, exceptionally),

    remove: (uuid, apply, exceptionally) => api.httpDelete(path(`${uuid}`), apply, exceptionally)
  }
}

export default new PersonApi(fetchApi, 'person')
