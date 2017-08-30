package lt.saltyjuice.dragas.chatty.v3.core.mock

open class FailingController : MockController()
{
    override fun mockTest(mockRequest: MockRequest): Boolean
    {
        return false
    }
}