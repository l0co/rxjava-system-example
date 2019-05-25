# RxJava System Example

This project contains sources for my [article about Reactive Systems with RxJava](http://lifeinide.com/post/2019-05-24-reactive-system-example-with-rxjava/).  

It contains three implementations of hypothetical web application backends:

1. `ThreadPerRequestTest` is standard blocking with thread-per-request processing model.
1. `ReactiveWithBlockingDbTest` is non-blocking reactive implementation but with database access written in a blocking way.
1. `ReactiveWithReactiveDbTest` is fully non blocking implementation with both server and database access reactive. 

## How to use

Start all tests with:

```bash
$ ./gradlew --info clean test
```

## One request test results

Example test results of one request for each provided implementation are included below:

### `ThreadPerRequestTest`

```
15 [main] New request connection established: 0
33 [WORKER-1] New request connection established: 0
2033 [WORKER-1] Received request: 0
2034 [WORKER-1] Doing some CPU-intensive operations
2534 [WORKER-1] Asking the database for data and exiting the worker thread
4535 [WORKER-1] Database data ready for: 0 with data: 0
4535 [WORKER-1] Response: 0 is ready, sending it to the client
```
 
### `ReactiveWithBlockingDbTest` 

```
3 [HTTP-PROCESSOR] Starting thread
47 [HTTP-ACCEPTOR] New request connection established: 0
51 [HTTP-ACCEPTOR] Beginning request subscription
2050 [HTTP-PROCESSOR] Request completed with data: 0
2053 [WORKER-1] Received request: 0
2053 [WORKER-1] Doing some CPU-intensive operations
2553 [WORKER-1] Asking the database for data and waiting for it
4554 [WORKER-1] Database data ready for: 0 with data: 0
4556 [WORKER-1] Response: 0 is ready, sending it to the client
```
 
### `ReactiveWithReactiveDbTest` 

```
0 [HTTP-PROCESSOR] Starting thread
0 [DB] Starting thread
1 [HTTP-ACCEPTOR] New request connection established: 0
1 [HTTP-ACCEPTOR] Beginning request subscription
2002 [HTTP-PROCESSOR] Request completed with data: 0
2002 [WORKER-2] Received request: 0
2003 [WORKER-2] Doing some CPU-intensive operations
2503 [WORKER-2] Asking the database for data and exiting the worker thread
4504 [DB] Database data ready for: 0 with data: 0
4504 [DB] Response: 0 is ready, sending it to the client
```

## Ten requests test results

Example test results of ten concurrent requests for each provided implementation are included below: 

### `ThreadPerRequestTest` 

```
1 [main] New request connection established: 0
9 [WORKER-2] New request connection established: 0
11 [main] New request connection established: 1
11 [WORKER-3] New request connection established: 1
21 [main] New request connection established: 2
22 [WORKER-1] New request connection established: 2
32 [main] New request connection established: 3
42 [main] New request connection established: 4
52 [main] New request connection established: 5
62 [main] New request connection established: 6
73 [main] New request connection established: 7
84 [main] New request connection established: 8
94 [main] New request connection established: 9
2009 [WORKER-2] Received request: 0
2010 [WORKER-2] Doing some CPU-intensive operations
2011 [WORKER-3] Received request: 1
2012 [WORKER-3] Doing some CPU-intensive operations
2022 [WORKER-1] Received request: 2
2022 [WORKER-1] Doing some CPU-intensive operations
2510 [WORKER-2] Asking the database for data and exiting the worker thread
2512 [WORKER-3] Asking the database for data and exiting the worker thread
2523 [WORKER-1] Asking the database for data and exiting the worker thread
4511 [WORKER-2] Database data ready for: 0 with data: 0
4511 [WORKER-2] Response: 0 is ready, sending it to the client
4512 [WORKER-2] New request connection established: 3
4513 [WORKER-3] Database data ready for: 1 with data: 2
4513 [WORKER-3] Response: 2 is ready, sending it to the client
4513 [WORKER-3] New request connection established: 4
4523 [WORKER-1] Database data ready for: 2 with data: 4
4524 [WORKER-1] Response: 4 is ready, sending it to the client
4524 [WORKER-1] New request connection established: 5
6512 [WORKER-2] Received request: 3
6513 [WORKER-2] Doing some CPU-intensive operations
6514 [WORKER-3] Received request: 4
6514 [WORKER-3] Doing some CPU-intensive operations
6525 [WORKER-1] Received request: 5
6525 [WORKER-1] Doing some CPU-intensive operations
7013 [WORKER-2] Asking the database for data and exiting the worker thread
7015 [WORKER-3] Asking the database for data and exiting the worker thread
7026 [WORKER-1] Asking the database for data and exiting the worker thread
9014 [WORKER-2] Database data ready for: 3 with data: 6
9014 [WORKER-2] Response: 6 is ready, sending it to the client
9015 [WORKER-2] New request connection established: 6
9015 [WORKER-3] Database data ready for: 4 with data: 8
9015 [WORKER-3] Response: 8 is ready, sending it to the client
9016 [WORKER-3] New request connection established: 7
9026 [WORKER-1] Database data ready for: 5 with data: 10
9027 [WORKER-1] Response: 10 is ready, sending it to the client
9027 [WORKER-1] New request connection established: 8
11015 [WORKER-2] Received request: 6
11016 [WORKER-2] Doing some CPU-intensive operations
11016 [WORKER-3] Received request: 7
11017 [WORKER-3] Doing some CPU-intensive operations
11028 [WORKER-1] Received request: 8
11028 [WORKER-1] Doing some CPU-intensive operations
11516 [WORKER-2] Asking the database for data and exiting the worker thread
11517 [WORKER-3] Asking the database for data and exiting the worker thread
11529 [WORKER-1] Asking the database for data and exiting the worker thread
13517 [WORKER-2] Database data ready for: 6 with data: 12
13517 [WORKER-2] Response: 12 is ready, sending it to the client
13517 [WORKER-3] Database data ready for: 7 with data: 14
13518 [WORKER-3] Response: 14 is ready, sending it to the client
13518 [WORKER-2] New request connection established: 9
13529 [WORKER-1] Database data ready for: 8 with data: 16
13530 [WORKER-1] Response: 16 is ready, sending it to the client
15518 [WORKER-2] Received request: 9
15519 [WORKER-2] Doing some CPU-intensive operations
16020 [WORKER-2] Asking the database for data and exiting the worker thread
18020 [WORKER-2] Database data ready for: 9 with data: 18
18021 [WORKER-2] Response: 18 is ready, sending it to the client
```
 
### `ReactiveWithBlockingDbTest` 

```
0 [HTTP-ACCEPTOR] New request connection established: 0
0 [HTTP-PROCESSOR] Starting thread
1 [HTTP-ACCEPTOR] Beginning request subscription
11 [HTTP-ACCEPTOR] New request connection established: 1
12 [HTTP-ACCEPTOR] Beginning request subscription
22 [HTTP-ACCEPTOR] New request connection established: 2
23 [HTTP-ACCEPTOR] Beginning request subscription
33 [HTTP-ACCEPTOR] New request connection established: 3
34 [HTTP-ACCEPTOR] Beginning request subscription
44 [HTTP-ACCEPTOR] New request connection established: 4
45 [HTTP-ACCEPTOR] Beginning request subscription
55 [HTTP-ACCEPTOR] New request connection established: 5
56 [HTTP-ACCEPTOR] Beginning request subscription
66 [HTTP-ACCEPTOR] New request connection established: 6
67 [HTTP-ACCEPTOR] Beginning request subscription
78 [HTTP-ACCEPTOR] New request connection established: 7
78 [HTTP-ACCEPTOR] Beginning request subscription
89 [HTTP-ACCEPTOR] New request connection established: 8
89 [HTTP-ACCEPTOR] Beginning request subscription
100 [HTTP-ACCEPTOR] New request connection established: 9
100 [HTTP-ACCEPTOR] Beginning request subscription
2000 [HTTP-PROCESSOR] Request completed with data: 0
2001 [WORKER-3] Received request: 0
2001 [WORKER-3] Doing some CPU-intensive operations
2101 [HTTP-PROCESSOR] Request completed with data: 1
2101 [WORKER-1] Received request: 1
2102 [WORKER-1] Doing some CPU-intensive operations
2202 [HTTP-PROCESSOR] Request completed with data: 2
2202 [WORKER-2] Received request: 2
2203 [WORKER-2] Doing some CPU-intensive operations
2303 [HTTP-PROCESSOR] Request completed with data: 3
2403 [HTTP-PROCESSOR] Request completed with data: 4
2502 [WORKER-3] Asking the database for data and waiting for it
2504 [HTTP-PROCESSOR] Request completed with data: 5
2602 [WORKER-1] Asking the database for data and waiting for it
2605 [HTTP-PROCESSOR] Request completed with data: 6
2703 [WORKER-2] Asking the database for data and waiting for it
2705 [HTTP-PROCESSOR] Request completed with data: 7
2806 [HTTP-PROCESSOR] Request completed with data: 8
2907 [HTTP-PROCESSOR] Request completed with data: 9
4502 [WORKER-3] Database data ready for: 0 with data: 0
4502 [WORKER-3] Response: 0 is ready, sending it to the client
4503 [WORKER-3] Received request: 3
4503 [WORKER-3] Doing some CPU-intensive operations
4602 [WORKER-1] Database data ready for: 1 with data: 2
4603 [WORKER-1] Response: 2 is ready, sending it to the client
4603 [WORKER-1] Received request: 4
4603 [WORKER-1] Doing some CPU-intensive operations
4704 [WORKER-2] Database data ready for: 2 with data: 4
4704 [WORKER-2] Response: 4 is ready, sending it to the client
4705 [WORKER-2] Received request: 5
4705 [WORKER-2] Doing some CPU-intensive operations
5003 [WORKER-3] Asking the database for data and waiting for it
5104 [WORKER-1] Asking the database for data and waiting for it
5205 [WORKER-2] Asking the database for data and waiting for it
7004 [WORKER-3] Database data ready for: 3 with data: 6
7004 [WORKER-3] Response: 6 is ready, sending it to the client
7004 [WORKER-3] Received request: 6
7005 [WORKER-3] Doing some CPU-intensive operations
7104 [WORKER-1] Database data ready for: 4 with data: 8
7105 [WORKER-1] Response: 8 is ready, sending it to the client
7105 [WORKER-1] Received request: 7
7105 [WORKER-1] Doing some CPU-intensive operations
7206 [WORKER-2] Database data ready for: 5 with data: 10
7206 [WORKER-2] Response: 10 is ready, sending it to the client
7206 [WORKER-2] Received request: 8
7207 [WORKER-2] Doing some CPU-intensive operations
7505 [WORKER-3] Asking the database for data and waiting for it
7605 [WORKER-1] Asking the database for data and waiting for it
7707 [WORKER-2] Asking the database for data and waiting for it
9505 [WORKER-3] Database data ready for: 6 with data: 12
9506 [WORKER-3] Response: 12 is ready, sending it to the client
9506 [WORKER-3] Received request: 9
9506 [WORKER-3] Doing some CPU-intensive operations
9606 [WORKER-1] Database data ready for: 7 with data: 14
9606 [WORKER-1] Response: 14 is ready, sending it to the client
9708 [WORKER-2] Database data ready for: 8 with data: 16
9708 [WORKER-2] Response: 16 is ready, sending it to the client
10007 [WORKER-3] Asking the database for data and waiting for it
12007 [WORKER-3] Database data ready for: 9 with data: 18
12007 [WORKER-3] Response: 18 is ready, sending it to the client
```
 
### `ReactiveWithReactiveDbTest` 

```
2 [HTTP-PROCESSOR] Starting thread
3 [DB] Starting thread
4 [HTTP-ACCEPTOR] New request connection established: 0
4 [HTTP-ACCEPTOR] Beginning request subscription
18 [HTTP-ACCEPTOR] New request connection established: 1
19 [HTTP-ACCEPTOR] Beginning request subscription
29 [HTTP-ACCEPTOR] New request connection established: 2
30 [HTTP-ACCEPTOR] Beginning request subscription
40 [HTTP-ACCEPTOR] New request connection established: 3
41 [HTTP-ACCEPTOR] Beginning request subscription
51 [HTTP-ACCEPTOR] New request connection established: 4
52 [HTTP-ACCEPTOR] Beginning request subscription
62 [HTTP-ACCEPTOR] New request connection established: 5
62 [HTTP-ACCEPTOR] Beginning request subscription
73 [HTTP-ACCEPTOR] New request connection established: 6
73 [HTTP-ACCEPTOR] Beginning request subscription
83 [HTTP-ACCEPTOR] New request connection established: 7
83 [HTTP-ACCEPTOR] Beginning request subscription
94 [HTTP-ACCEPTOR] New request connection established: 8
94 [HTTP-ACCEPTOR] Beginning request subscription
104 [HTTP-ACCEPTOR] New request connection established: 9
104 [HTTP-ACCEPTOR] Beginning request subscription
2005 [HTTP-PROCESSOR] Request completed with data: 0
2005 [WORKER-1] Received request: 0
2005 [WORKER-1] Doing some CPU-intensive operations
2105 [HTTP-PROCESSOR] Request completed with data: 1
2106 [WORKER-2] Received request: 1
2106 [WORKER-2] Doing some CPU-intensive operations
2206 [HTTP-PROCESSOR] Request completed with data: 2
2206 [WORKER-3] Received request: 2
2207 [WORKER-3] Doing some CPU-intensive operations
2307 [HTTP-PROCESSOR] Request completed with data: 3
2407 [HTTP-PROCESSOR] Request completed with data: 4
2505 [WORKER-1] Asking the database for data and exiting the worker thread
2505 [WORKER-1] Received request: 3
2506 [WORKER-1] Doing some CPU-intensive operations
2508 [HTTP-PROCESSOR] Request completed with data: 5
2606 [WORKER-2] Asking the database for data and exiting the worker thread
2607 [WORKER-2] Received request: 4
2607 [WORKER-2] Doing some CPU-intensive operations
2608 [HTTP-PROCESSOR] Request completed with data: 6
2707 [WORKER-3] Asking the database for data and exiting the worker thread
2708 [WORKER-3] Received request: 5
2708 [WORKER-3] Doing some CPU-intensive operations
2709 [HTTP-PROCESSOR] Request completed with data: 7
2809 [HTTP-PROCESSOR] Request completed with data: 8
2910 [HTTP-PROCESSOR] Request completed with data: 9
3006 [WORKER-1] Asking the database for data and exiting the worker thread
3006 [WORKER-1] Received request: 6
3007 [WORKER-1] Doing some CPU-intensive operations
3108 [WORKER-2] Asking the database for data and exiting the worker thread
3108 [WORKER-2] Received request: 7
3109 [WORKER-2] Doing some CPU-intensive operations
3209 [WORKER-3] Asking the database for data and exiting the worker thread
3209 [WORKER-3] Received request: 8
3209 [WORKER-3] Doing some CPU-intensive operations
3507 [WORKER-1] Asking the database for data and exiting the worker thread
3507 [WORKER-1] Received request: 9
3507 [WORKER-1] Doing some CPU-intensive operations
3609 [WORKER-2] Asking the database for data and exiting the worker thread
3710 [WORKER-3] Asking the database for data and exiting the worker thread
4008 [WORKER-1] Asking the database for data and exiting the worker thread
4505 [DB] Database data ready for: 0 with data: 0
4506 [DB] Response: 0 is ready, sending it to the client
4608 [DB] Database data ready for: 1 with data: 2
4608 [DB] Response: 2 is ready, sending it to the client
4709 [DB] Database data ready for: 2 with data: 4
4709 [DB] Response: 4 is ready, sending it to the client
5006 [DB] Database data ready for: 3 with data: 6
5006 [DB] Response: 6 is ready, sending it to the client
5108 [DB] Database data ready for: 4 with data: 8
5108 [DB] Response: 8 is ready, sending it to the client
5209 [DB] Database data ready for: 5 with data: 10
5209 [DB] Response: 10 is ready, sending it to the client
5508 [DB] Database data ready for: 6 with data: 12
5508 [DB] Response: 12 is ready, sending it to the client
5609 [DB] Database data ready for: 7 with data: 14
5609 [DB] Response: 14 is ready, sending it to the client
5711 [DB] Database data ready for: 8 with data: 16
5711 [DB] Response: 16 is ready, sending it to the client
6008 [DB] Database data ready for: 9 with data: 18
6008 [DB] Response: 18 is ready, sending it to the client
```
