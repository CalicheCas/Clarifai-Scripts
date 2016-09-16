/**
 * Created by carlo on 8/13/16.
 */
import static groovyx.net.http.ContentType.JSON
import com.apipulse.afconnector.AFConnectorWrapper
import groovy.json.JsonOutput
import groovyx.net.http.*
import static groovyx.net.http.Method.POST

public class ClarifaiMakeModel extends AFConnectorWrapper{

    private static POST_URL = 'https://api2-prod.clarifai.com/v2/models'
    public void run(){

        final String accessToken  = params['accessToken']

        def payload = [
                'model':[
                        'name': 'face_ emotions',
                        'input_info':[
                                'image_input_info':{}
                        ],
                        'output_info':[
                                'concept_output_info':[
                                        'concepts':[
                                                [
                                                   'id': 'happy'
                                                ],
                                                [
                                                   'id': 'sad'
                                                ],
                                                [
                                                   'id': 'angry'
                                                ],
                                                [
                                                   'id': 'funny'
                                                ],
                                                [
                                                   'id':'worried'
                                                ],
                                                [
                                                   'id': 'robot'
                                                ]
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