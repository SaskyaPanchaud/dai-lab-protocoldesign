**Section 1: Overview**

calculation is a client-server protocol. The client connects to a server and ask to calculate something. The server sends the result or an error message, if the calcul is impossible.

**Section 2: Transport layer protocol**

calculation uses TCP. The client establishes the connection. It has to know the IP address of the server. The server listens on TCP port ???. The client closes the connection with the message "END". If there is an error message, the server closes the connection.

**Section 3: Messages**

- CALCULATE x y op
- RESULT a
- ERROR e
- END

**Section 4: Specific elements**
- operations : +, -, *, / (with or without parenthesis) 
- numbers : int and float
- errors : divide per zero, operation unknown, connection impossible

**Section 5: Example dialogs**

- c open, c CALCULATE, s RESULT, c CALCULATE, s RESULT, ..., c END, c close
- c open, c CALCULATE, s RESULT, ..., c CALCULATE, s ERROR, s close