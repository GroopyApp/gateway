package app.groopy.gateway.presentation

import app.groopy.gateway.application.GatewayService
import app.groopy.gateway.application.validators.GatewayValidator
import app.groopy.gateway.infrastructure.InfrastructureService
import app.groopy.gateway.traits.SampleProtoData
import org.spockframework.spring.SpringBean
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class GatewayControllerTest extends Specification implements SampleProtoData {

    @SpringBean
    private GatewayService service = Mock GatewayService

    @Subject
    def testSubject = new GatewayController(service)

    @Unroll
    def "when a gateway #requestName request is performed, a Gateway response is sent"() {

        given:
        service.get(request) >> response

        when:
        def gatewayResponse = testSubject.gateway(request)

        then:
        gatewayResponse.getBody() == sampleGatewayResponse(response)

        where:
        request                           | response                            || requestName
        sampleProtoSignUpRequest()        | sampleProtoSignUpResponse()         || "signUp"
        sampleProtoSignInRequest()        | sampleProtoSignInResponse()         || "signIn"
        sampleProtoCreateRoomRequest()    | sampleProtoCreateRoomResponse()     || "createRoom"
        sampleProtoListRoomRequest()      | sampleProtoListRoomResponse()       || "listRoom"
        sampleProtoSubscribeRoomRequest() | sampleProtoSubscribeRoomResponse()  || "subscribeRoom"
    }
}
