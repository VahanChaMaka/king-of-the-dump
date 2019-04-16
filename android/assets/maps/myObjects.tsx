<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.2.2" name="myObjects" tilewidth="100" tileheight="152" tilecount="2" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="true"/>
   <property name="impassable" type="bool" value="true"/>
   <property name="door" type="bool" value="true"/>
   <property name="stateIds" value="{&quot;closed&quot;: 0, &quot;opened&quot;: 1}"/>
  </properties>
  <image width="64" height="151" source="objects/door_closed.png"/>
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
