package app.groopy.gateway.application

import app.groopy.gateway.application.validators.GatewayValidator
import app.groopy.gateway.infrastructure.InfrastructureService
import app.groopy.gateway.traits.SampleProtoData
import org.spockframework.spring.SpringBean
import spock.lang.Specification
import spock.lang.Subject

class GatewayServiceTest extends Specification implements SampleProtoData {

    @SpringBean
    private GatewayValidator validator = Mock GatewayValidator
    @SpringBean
    private InfrastructureService infrastructureService = Mock InfrastructureService

    @Subject
    def testSubject = new GatewayService(validator, infrastructureService)

    def "when a gateway signUp request is performed, user-service is invoked"() {

        given:
        def protoSignUpRequest = sampleProtoSignUpRequest()
        def protoSignUpResponse = sampleProtoSignUpResponse()

        and:
        infrastructureService.signUp(protoSignUpRequest) >> protoSignUpResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoSignUpRequest))

        then:
        response == protoSignUpResponse
    }

    def "when a gateway signIn request is performed, user-service is invoked"() {

        given:
        def protoSignInRequest = sampleProtoSignInRequest()
        def protoSignInResponse = sampleProtoSignInResponse()

        and:
        infrastructureService.signIn(protoSignInRequest) >> protoSignInResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoSignInRequest))

        then:
        response == protoSignInResponse
    }

    def "when a gateway createRoom request is performed, room-service is invoked"() {

        given:
        def protoCreateRoomRequest = sampleProtoCreateRoomRequest()
        def protoCreateRoomResponse = sampleProtoCreateRoomResponse()

        and:
        infrastructureService.createRoom(protoCreateRoomRequest) >> protoCreateRoomResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoCreateRoomRequest))

        then:
        response == protoCreateRoomResponse
    }

    def "when a gateway listRoom request is performed, room-service is invoked"() {

        given:
        def protoListRoomRequest = sampleProtoListRoomRequest()
        def protoListRoomResponse = sampleProtoListRoomResponse()

        and:
        infrastructureService.searchRooms(protoListRoomRequest) >> protoListRoomResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoListRoomRequest))

        then:
        response == protoListRoomResponse
    }

    def "when a gateway userSubscribedRoom request is performed, room-service is invoked"() {

        given:
        def protoUserRoomsRequest = sampleProtoUserRoomsRequest()
        def protoListRoomResponse = sampleProtoListRoomResponse()

        and:
        infrastructureService.listRoom(protoUserRoomsRequest) >> protoListRoomResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoUserRoomsRequest))

        then:
        response == protoListRoomResponse
    }

    def "when a gateway subscribe request is performed, room-service is invoked"() {

        given:
        def protoSubscribeRoomRequest = sampleProtoSubscribeRoomRequest()
        def protoSubscribeRoomResponse = sampleProtoSubscribeRoomResponse()

        and:
        infrastructureService.subscribeToRoom(protoSubscribeRoomRequest) >> protoSubscribeRoomResponse

        when:
        def response = testSubject.get(sampleGatewayRequest(protoSubscribeRoomRequest))

        then:
        response == protoSubscribeRoomResponse
    }

}
