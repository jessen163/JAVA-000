学习笔记
<p>1、（必做）配置redis的主从复制，sentinel高可用，Cluster集群。 提交如下内容到github： </p>
<p>1）config配置文件， </p>
<p>2）启动和操作、验证集群下数据读写的命令步骤。</p>



<p>1、取redis.conf为默认的配置文件，端口是6379</p>
<p>2、拷贝redis.conf，生成redis_6380.conf,修改端口为6380，登录后，执行下面命令，主从复制搭建完成。</p>
<p>slaveof localhost 6379</p>

<p>sentinel高可用：</p>
<p>sentinel.conf配置： sentinel monitor mymaster 127.0.0.1 6379 2 </p>
<p>sentinel down-after-milliseconds mymaster 60000 </p>
<p>sentinel failover-timeout mymaster 180000 </p>
<p>sentinel parallel-syncs mymaster 1</p>


<p>Cluster集群</p>
