<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.2.2" name="myObjects" tilewidth="100" tileheight="152" tilecount="2" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0">
  <properties>
   <property name="action" value="open"/>
   <property name="impassable" type="bool" value="true"/>
  </properties>
  <image width="64" height="151" source="objects/door_closed.png"/>
 </tile>
 <tile id="1">
  <properties>
   <property name="action" value="open"/>
   <property name="impassable" type="bool" value="false"/>
  </properties>
  <image width="100" height="152" source="objects/door_opened.png"/>
 </tile>
</tileset>
