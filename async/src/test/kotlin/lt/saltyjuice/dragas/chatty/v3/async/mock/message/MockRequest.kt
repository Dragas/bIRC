package lt.saltyjuice.dragas.chatty.v3.async.mock.message

class MockRequest(val value: Int)
{
    operator fun rem(int: Int): Int
    {
        return value % int
    }
}