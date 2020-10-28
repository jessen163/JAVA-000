学习笔记
本周主要学习了串行GC、并行GC、CMS GC、G1 GC 的日志进行了研究，相对来说1、cms、g1的日志内容较为复杂 2、内存较小的情况，生成的对象相差不太大 3、内存较大的情况下，G1、CMS、并行GC优于串行GC


本机执行情况：
串行GC：
java -XX:+UseSerialGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
正在执行...
执行结束!共生成对象次数:9512

生成gc日志结果：
Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for bsd-amd64 JRE (1.8.0_231-b11), built on Oct  5 2019 03:15:25 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 16777216k(647168k free)
          
/proc/meminfo:
          
CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseSerialGC
2020-10-28T22:56:57.420-0800: 0.136: [GC (Allocation Failure) 2020-10-28T22:56:57.421-0800: 0.136: [DefNew: 139776K->17472K(157248K), 0.0230322 secs] 139776K->45433K(506816K), 0.0231066 secs] [Times: user=0.01 sys=0.01, real=0.02 secs]
2020-10-28T22:56:57.470-0800: 0.186: [GC (Allocation Failure) 2020-10-28T22:56:57.470-0800: 0.186: [DefNew: 157248K->17470K(157248K), 0.0307387 secs] 185209K->87940K(506816K), 0.0307969 secs] [Times: user=0.01 sys=0.01, real=0.03 secs]
2020-10-28T22:56:57.525-0800: 0.241: [GC (Allocation Failure) 2020-10-28T22:56:57.525-0800: 0.241: [DefNew: 157246K->17471K(157248K), 0.0236536 secs] 227716K->129895K(506816K), 0.0237092 secs] [Times: user=0.01 sys=0.01, real=0.02 secs]
2020-10-28T22:56:57.572-0800: 0.288: [GC (Allocation Failure) 2020-10-28T22:56:57.572-0800: 0.288: [DefNew: 157247K->17471K(157248K), 0.0243224 secs] 269671K->174184K(506816K), 0.0243698 secs] [Times: user=0.02 sys=0.01, real=0.02 secs]
@                                                                               
"gc.demo.log" 33L, 6478C



并行GC：
java -XX:+UseParallelGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis

执行结束!共生成对象次数:8426

生成gc日志结果：
Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for bsd-amd64 JRE (1.8.0_231-b11), built on Oct  5 2019 03:15:25 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 16777216k(432408k free)
          
/proc/meminfo:
          
CommandLine flags: -XX:InitialHeapSize=268435456 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseParallelGC
2020-10-28T22:55:00.989-0800: 0.112: [GC (Allocation Failure) [PSYoungGen: 65456K->10744K(76288K)] 65456K->25946K(251392K), 0.0111435 secs] [Times: user=0.02 sys=0.07, real=0.02 secs]
2020-10-28T22:55:01.011-0800: 0.134: [GC (Allocation Failure) [PSYoungGen: 76280K->10746K(141824K)] 91482K->48247K(316928K), 0.0118851 secs] [Times: user=0.02 sys=0.07, real=0.01 secs] 
2020-10-28T22:55:01.062-0800: 0.185: [GC (Allocation Failure) [PSYoungGen: 141818K->10746K(141824K)] 179319K->91707K(316928K), 0.0193351 secs] [Times: user=0.03 sys=0.12, real=0.02 secs] 
2020-10-28T22:55:01.097-0800: 0.220: [GC (Allocation Failure) [PSYoungGen: 141818K->10741K(163840K)] 222779K->136389K(338944K), 0.0190044 secs] [Times: user=0.03 sys=0.12, real=0.02 secs]
@                                                                               
"gc.demo.log" 52L, 8797C

cms GC:
java -XX:+UseConcMarkSweepGC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps GCLogAnalysis
执行结束!共生成对象次数:10154

生成gc日志结果：
Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for bsd-amd64 JRE (1.8.0_231-b11), built on Oct  5 2019 03:15:25 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 16777216k(678204k free)
          
/proc/meminfo:
          
CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:MaxNewSize=178958336 -XX:MaxTenuringThreshold=6 -XX:NewSize=178958336 -XX:OldPLABSize=16 -XX:OldSize=357912576 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseConcMarkSweepGC -XX:+UseParNewGC
2020-10-28T22:59:06.788-0800: 0.132: [GC (Allocation Failure) 2020-10-28T22:59:06.788-0800: 0.132: [ParNew: 139776K->17471K(157248K), 0.0172448 secs] 139776K->42624K(506816K), 0.0173279 secs] [Times: user=0.03 sys=0.09, real=0.02 secs]
2020-10-28T22:59:06.823-0800: 0.166: [GC (Allocation Failure) 2020-10-28T22:59:06.823-0800: 0.166: [ParNew: 157247K->17472K(157248K), 0.0175007 secs] 182400K->86377K(506816K), 0.0175650 secs] [Times: user=0.04 sys=0.11, real=0.02 secs]
2020-10-28T22:59:06.856-0800: 0.199: [GC (Allocation Failure) 2020-10-28T22:59:06.856-0800: 0.199: [ParNew: 157248K->17472K(157248K), 0.0225379 secs] 226153K->128297K(506816K), 0.0226338 secs] [Times: user=0.21 sys=0.01, real=0.02 secs]
@                                                                               @                                                                               
"gc.demo.log" 119L, 18642C

G1 gc:
java -XX:+UseG1GC -Xms512m -Xmx512m -Xloggc:gc.demo.log -XX:+PrintGC -XX:+PrintGCDateStamps GCLogAnalysis

执行结束!共生成对象次数:9832

生成gc日志结果：
Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for bsd-amd64 JRE (1.8.0_231-b11), built on Oct  5 2019 03:15:25 by "java_re" with gcc 4.2.1 (Based on Apple Inc. build 5658) (LLVM build 2336.11.00)
Memory: 4k page, physical 16777216k(370020k free)

/proc/meminfo:

CommandLine flags: -XX:InitialHeapSize=536870912 -XX:MaxHeapSize=536870912 -XX:+PrintGC -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:+UseG1GC
2020-10-28T23:02:53.673-0800: 0.089: [GC pause (G1 Evacuation Pause) (young) 29M->10M(512M), 0.0043373 secs]
2020-10-28T23:02:53.683-0800: 0.099: [GC pause (G1 Evacuation Pause) (young) 39M->21M(512M), 0.0045493 secs]
2020-10-28T23:02:53.713-0800: 0.129: [GC pause (G1 Evacuation Pause) (young) 85M->43M(512M), 0.0081365 secs]
2020-10-28T23:02:53.744-0800: 0.160: [GC pause (G1 Evacuation Pause) (young) 120M->67M(512M), 0.0092156 secs]
2020-10-28T23:02:53.771-0800: 0.187: [GC pause (G1 Evacuation Pause) (young) 152M->95M(512M), 0.0079314 secs]
2020-10-28T23:02:53.806-0800: 0.222: [GC pause (G1 Evacuation Pause) (young) 203M->128M(512M), 0.0115596 secs]
2020-10-28T23:02:53.898-0800: 0.315: [GC pause (G1 Humongous Allocation) (young) (initial-mark)-- 394M->223M(512M), 0.0475431 secs]
2020-10-28T23:02:53.946-0800: 0.362: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:53.946-0800: 0.362: [GC concurrent-root-region-scan-end, 0.0001031 secs]
2020-10-28T23:02:53.946-0800: 0.362: [GC concurrent-mark-start]
2020-10-28T23:02:53.951-0800: 0.367: [GC concurrent-mark-end, 0.0049909 secs]
2020-10-28T23:02:53.951-0800: 0.367: [GC remark, 0.0007901 secs]
2020-10-28T23:02:53.952-0800: 0.368: [GC cleanup 253M->253M(512M), 0.0003645 secs]
2020-10-28T23:02:53.971-0800: 0.388: [GC pause (G1 Evacuation Pause) (young) 337M->255M(512M), 0.0110393 secs]
2020-10-28T23:02:53.985-0800: 0.401: [GC pause (G1 Evacuation Pause) (mixed) 269M->228M(512M), 0.0037069 secs]
2020-10-28T23:02:53.989-0800: 0.405: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 230M->229M(512M), 0.0013215 secs]
2020-10-28T23:02:53.990-0800: 0.406: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:53.990-0800: 0.407: [GC concurrent-root-region-scan-end, 0.0001051 secs]
2020-10-28T23:02:53.990-0800: 0.407: [GC concurrent-mark-start]
2020-10-28T23:02:53.991-0800: 0.408: [GC concurrent-mark-end, 0.0010117 secs]
2020-10-28T23:02:53.991-0800: 0.408: [GC remark, 0.0011449 secs]
2020-10-28T23:02:53.993-0800: 0.409: [GC cleanup 234M->231M(512M), 0.0007889 secs]
2020-10-28T23:02:53.993-0800: 0.410: [GC concurrent-cleanup-start]
2020-10-28T23:02:53.994-0800: 0.410: [GC concurrent-cleanup-end, 0.0000118 secs]
2020-10-28T23:02:54.025-0800: 0.441: [GC pause (G1 Evacuation Pause) (young)-- 424M->348M(512M), 0.0036050 secs]
2020-10-28T23:02:54.030-0800: 0.446: [GC pause (G1 Evacuation Pause) (mixed) 355M->339M(512M), 0.0037802 secs]
2020-10-28T23:02:54.035-0800: 0.451: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 350M->342M(512M), 0.0013361 secs]
2020-10-28T23:02:54.036-0800: 0.453: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:54.037-0800: 0.453: [GC concurrent-root-region-scan-end, 0.0001023 secs]
2020-10-28T23:02:54.037-0800: 0.453: [GC concurrent-mark-start]
2020-10-28T23:02:54.037-0800: 0.454: [GC concurrent-mark-end, 0.0008151 secs]
2020-10-28T23:02:54.037-0800: 0.454: [GC remark, 0.0011849 secs]
2020-10-28T23:02:54.039-0800: 0.455: [GC cleanup 347M->346M(512M), 0.0007615 secs]
2020-10-28T23:02:54.040-0800: 0.456: [GC concurrent-cleanup-start]
2020-10-28T23:02:54.040-0800: 0.456: [GC concurrent-cleanup-end, 0.0000082 secs]
2020-10-28T23:02:54.051-0800: 0.467: [GC pause (G1 Evacuation Pause) (young) 419M->369M(512M), 0.0030739 secs]
2020-10-28T23:02:54.057-0800: 0.474: [GC pause (G1 Evacuation Pause) (mixed) 389M->329M(512M), 0.0028847 secs]
2020-10-28T23:02:54.065-0800: 0.481: [GC pause (G1 Evacuation Pause) (mixed) 356M->306M(512M), 0.0042149 secs]
2020-10-28T23:02:54.074-0800: 0.490: [GC pause (G1 Evacuation Pause) (mixed) 332M->303M(512M), 0.0029653 secs]
2020-10-28T23:02:54.078-0800: 0.495: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 312M->305M(512M), 0.0014218 secs]
2020-10-28T23:02:54.080-0800: 0.496: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:54.080-0800: 0.496: [GC concurrent-root-region-scan-end, 0.0001168 secs]
2020-10-28T23:02:54.080-0800: 0.496: [GC concurrent-mark-start]
2020-10-28T23:02:54.081-0800: 0.497: [GC concurrent-mark-end, 0.0008391 secs]
2020-10-28T23:02:54.081-0800: 0.497: [GC remark, 0.0013190 secs]
2020-10-28T23:02:54.082-0800: 0.499: [GC cleanup 310M->310M(512M), 0.0008375 secs]
2020-10-28T23:02:54.098-0800: 0.514: [GC pause (G1 Evacuation Pause) (young) 402M->332M(512M), 0.0048025 secs]
2020-10-28T23:02:54.105-0800: 0.521: [GC pause (G1 Evacuation Pause) (mixed) 348M->307M(512M), 0.0051355 secs]
2020-10-28T23:02:54.115-0800: 0.531: [GC pause (G1 Evacuation Pause) (mixed) 334M->316M(512M), 0.0022979 secs]
2020-10-28T23:02:54.117-0800: 0.533: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 316M->315M(512M), 0.0013876 secs]
2020-10-28T23:02:54.118-0800: 0.535: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:54.119-0800: 0.535: [GC concurrent-root-region-scan-end, 0.0000949 secs]
2020-10-28T23:02:54.119-0800: 0.535: [GC concurrent-mark-start]
2020-10-28T23:02:54.119-0800: 0.536: [GC concurrent-mark-end, 0.0008234 secs]
2020-10-28T23:02:54.119-0800: 0.536: [GC remark, 0.0014397 secs]
2020-10-28T23:02:54.121-0800: 0.537: [GC cleanup 319M->318M(512M), 0.0007791 secs]
2020-10-28T23:02:54.122-0800: 0.538: [GC concurrent-cleanup-start]
2020-10-28T23:02:54.122-0800: 0.538: [GC concurrent-cleanup-end, 0.0000106 secs]
2020-10-28T23:02:54.135-0800: 0.551: [GC pause (G1 Evacuation Pause) (young) 405M->344M(512M), 0.0038970 secs]
2020-10-28T23:02:54.142-0800: 0.558: [GC pause (G1 Evacuation Pause) (mixed) 360M->326M(512M), 0.0046186 secs]
2020-10-28T23:02:54.147-0800: 0.563: [GC pause (G1 Humongous Allocation) (young) (initial-mark) 327M->326M(512M), 0.0013964 secs]
2020-10-28T23:02:54.148-0800: 0.565: [GC concurrent-root-region-scan-start]
2020-10-28T23:02:54.149-0800: 0.565: [GC concurrent-root-region-scan-end, 0.0000989 secs]
2020-10-28T23:02:54.149-0800: 0.565: [GC concurrent-mark-start]
