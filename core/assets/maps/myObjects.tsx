<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.5" tiledversion="1.7.2" name="myObjects" tilewidth="100" tileheight="152" tilecount="2" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="true"/>
   <property name="door" type="bool" value="true"/>
   <property name="impassable" type="bool" value="true"/>
   <property name="sound" value="{&quot;1&quot;: &quot;doorClose_4.ogg&quot;, &quot;2&quot;: &quot;doorOpen_1.ogg&quot;}"/>
   <property name="stateIds" value="{&quot;closed&quot;: 0, &quot;opened&quot;: 1}"/>
  </properties>
  <image width="100" height="151" source="objects/door_closed.png"/>
 </tile>
 <tile id="1">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="false"/>
   <property name="impassable" type="bool" value="false"/>
   <property name="stateIds" value="{&quot;closed&quot;: 0, &quot;opened&quot;: 1}"/>
  </properties>
  <image width="100" height="152" source="objects/door_opened.png"/>
 </tile>
</tileset>
