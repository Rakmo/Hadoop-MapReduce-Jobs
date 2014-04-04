*********** INSTRUCTIONS ***********
1. Copying Data_HW_1 folder from HDFS to /users/<net-id>/
2. All the JAR files in the zip folder should be copied to Hadoop folder on your HOME Unix directory


Part 1: Find top 10 average rated movies with descending order of rating (Use of chaining of multiple map-reduce job is a must here.)
> hadoop jar Hadoop/A1P1.jar A1Part1 /user/oxk120630/Data_HW_1/ratings.dat /user/oxk120630/subpart1 /user/oxk120630/p1
> hadoop fs -cat /user/oxk120630/p1/part-r-00000


Part 2: List each genre of movie and count the movies of each genre.
> hadoop jar Hadoop/A1P2.jar A1Part2 /user/oxk120630/Data_HW_1/movies.dat /user/oxk120630/p2
> hadoop fs -cat /user/oxk120630/p2/part-r-00000


Part 3: Given some genres, find all the movies belong to that genre.
> hadoop jar Hadoop/A1P3.jar A1Part3 /user/oxk120630/Data_HW_1/movies.dat /user/oxk120630/p3 Action Comedy
> hadoop fs -cat /user/oxk120630/p3/part-r-00000


Note: Eclipse IDE is used to create all the JAR files. 
- By Omkar Kannav




Outputs:

 hadoop fs -cat /user/oxk120630/part3/part-r-00000
Princess Bride, The (1987)
Army of Darkness (1993)
Blues Brothers, The (1980)
Evil Dead II (Dead By Dawn) (1987)
Butch Cassidy and the Sundance Kid (1969)
Batman Returns (1992)
Young Guns (1988)
Young Guns II (1990)
Mars Attacks! (1996)
Beverly Hills Ninja (1997)
Best Men (1997)
Men in Black (1997)
Money Talks (1997)
Blues Brothers 2000 (1998)
Mr. Nice Guy (1997)
Big Hit, The (1998)
I Went Down (1997)
Buffalo 66 (1998)
Lethal Weapon 4 (1998)
{cs6360:~} hadoop fs -cat /user/oxk120630/part2/part-r-00000
Action          140
Adventure       77
Animation       22
Children's      66
Comedy          270
Crime           65
Documentary     39
Drama           413
Fantasy         14
Film-Noir       16
Horror          60
Musical         36
Mystery         36
Romance         126
Sci-Fi          62
Thriller        128
War             53
Western         15
{cs6360:~} hadoop fs -cat /user/oxk120630/p2/part-r-00000
989     9.666666666666666
789     9.0
3881    9.0
3233    9.0
3820    8.027027027027026
1830    7.5
3172    5.5
3656    5.0
3607    5.0
3382    5.0





Action          140
Adventure       77
Animation       22
Children's      66
Comedy          270
Crime           65
Documentary     39
Drama           413
Fantasy         14
Film-Noir       16
Horror          60
Musical         36
Mystery         36
Romance         126
Sci-Fi          62
Thriller        128
War             53
Western         15
