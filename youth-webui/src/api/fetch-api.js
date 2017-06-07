import axios from 'axios'
import config from '../config'

axios.defaults.baseURL = config.baseUrl

const FetchApi = (http) => {
  const resolve = (method, apply, exceptionally) => method.then(res => apply(res.data)).catch(exceptionally)

  return {
    httpGet: (url, apply, exceptionally) => resolve(http.get(url), apply, exceptionally),

    httpPost: (url, payload, apply, exceptionally) => resolve(http.post(url, payload), apply, exceptionally),

    httpPut: (url, payload, apply, exceptionally) => resolve(http.put(url, payload), apply, exceptionally),

    httpDelete: (url, apply, exceptionally) => resolve(http.delete(url), apply, exceptionally)
  }
}

export default new FetchApi(axios)
