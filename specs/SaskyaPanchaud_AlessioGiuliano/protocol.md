# ACP: Amazing Calculator Protocol

## Overview
The goal of this protocol is to be able to perform binary mathematical operations on a server following statements sent by a client.

## Transport layer protocol
ACP uses TCP. The client establishes the connection. It has to know the IP address of the server. The server listens on TCP port 123456.

The server closes the connection when the result of the operation has been sent, or after sending an error message if the expression sent by the client is invalid.

## Messages

- &lt;OPERATION> &lt;operand1> &lt;operand2>
  - The client asks for the result of an operation between two operands. The possible operations are: 
    - ADD
    - SUBSTRACT
    - MULTIPLY
    - DIVIDE
- RESULT &lt;result>
  - Message sent by the server containing the string with the operation's result.
- UNKNOWN OPERATION &lt;OPERATION>
  - Error response message from the server, indicating that the requested operation is not valid.
- INVALID OPERANDS COUNT &lt;operands count>
  - Error response message from the server, indicating that the client did not put exactly two operands in the expression.
- INVALID OPERAND &lt;operand>
  - Error message from the server, indicating that an operand sent by the client are is not a valid number.
- ZERO DIVISION
  - Error message from the server, indicating that the client is attempting to divide by 0.
- UNKNOWN ERROR
  - Generic error message sent by the server, indicating that an unexpected error happened during the process.
  
## Example dialogs

### Successful operation

Client -------- Server

Open TCP connection >

ADD 40 2 >

< RESULT 42

< Close TCP connection

### Unsuccessful operations

Client -------- Server

Open TCP connection >

MODULO 59 2 >

< INVALID OPERATION MODULO

< Close TCP connection

------------

Client -------- Server

Open TCP connection >

ADD 40 1 1 >

< INVALID OPERANDS COUNT 3

< Close TCP connection

-----------

Client -------- Server

Open TCP connection >

MULTIPLY 10 NOT_A_NUMBER >

< INVALID OPERAND NOT_A_NUMBER

< Close TCP connection