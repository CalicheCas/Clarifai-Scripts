/**
 * Created by carlo on 8/13/16.
 */
import static groovyx.net.http.ContentType.JSON
import com.apipulse.afconnector.AFConnectorWrapper
import groovy.json.JsonOutput
import groovyx.net.http.*
import static groovyx.net.http.Method.POST

public class ClarifaiCall extends AFConnectorWrapper{

    private static POST_URL = 'https://api2-prod.clarifai.com/v2/inputs'
    public void run(){

        final String accessToken  = params['accessToken']
        /*final String happyFace     = params['happy']
        final String sadFace     = params['sad']
        final String worriedFace     = params['worried']
        final String funnyFace     = params['funny']
        final String angryFace     = params['angry']*/
        final String robot         = params['robot']

        def payload = ['inputs':[
                            [
                                    'data':[
                                            'image':['url':  "$happyFace"],
                                    'tags':[
                                            ['concept':['id':'face'], 'value': true],
                                            ['concept':['id':'happy'], 'value': true]
                                           ]
                                    ]
                            ],
                            [
                                    'data':[
                                            'image':['url':  "$sadFace"],
                                    'tags':[
                                             ['concept':['id':'face'], 'value': true],
                                             ['concept':['id':'sad'], 'value': true]
                                            ]
                                    ]
                            ],
                            [
                                    'data':[
                                            'image':['url':  "$worriedFace"],
                                            'tags':[
                                                    ['concept':['id':'face'], 'value': true],
                                                    ['concept':['id':'worried'], 'value': true]
                                            ]
                                    ]
                            ],
                            [
                                    'data':[
                                            'image':['url':  "$angryFace"],
                                            'tags':[
                                                    ['concept':['id':'face'], 'value': true],
                                                    ['concept':['id':'angry'], 'value': true]
                                            ]
                                    ]
                            ],
                            [
                                    'data':[
                                            'image':['url':  "$funnyFace"],
                                            'tags':[
                                                    ['concept':['id':'face'], 'value': true],
                                                    ['concept':['id':'funny'], 'value': true]
                                            ]
                                    ]
                            ],
                            [
                                    'data':[
                                            'image':['url':  "$robot"],
                                            'tags':[
                                                    ['concept':['id':'face'], 'value': false],
                                                    ['concept':['id':'robot'], 'value': true]
                                            ]
                                    ]
                            ]
                         ]
                       ]

        payload = JsonOutput.toJson(payload)
        println payload

        //Posting images to data base and tag it.
        final HTTPBuilder httpp = new HTTPBuilder(POST_URL)
        httpp.headers['Authorization'] = "Bearer $accessToken "
        httpp.headers.'Content-Type' = 'application/json'
        httpp.request(POST,JSON) {
            body = payload
            response.success = { resp, reader ->
                println "Success! ${resp.status}"
                System.out << reader

            }
            response.failure = { resp, reader ->
                println "Request failed with status ${resp.status}"
                System.out << reader
            }
        }
    }

}