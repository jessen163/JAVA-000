<p>学习笔记</p>
<p>第二个版本：自定义 Queue---完成</p></p>
<p> 2、去掉内存Queue，设计自定义Queue，实现消息确认和消费offset</p>
<p> 1）自定义内存Message数组模拟Queue。</p>
<p> 2）使用指针记录当前消息写入位置。</p>
<p> 3）对于每个命名消费者，用指针记录消费位置。</p>
<p>第三个版本：基于 SpringMVC 实现 MQServer</p>
<p> 3、拆分broker和client(包括producer和consumer)</p>
<p> 1）将Queue保存到web server端</p>
<p> 2）设计消息读写API接口，确认接口，提交offset接口</p>
<p> 3）producer和consumer通过httpclient访问Queue</p>
<p> 4）实现消息确认，offset提交</p>
<p> 5）实现consumer从offset增量拉取</p>
