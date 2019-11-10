<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.2.5" name="Fence" tilewidth="96" tileheight="156" tilecount="50" columns="10">
 <image source="big/Walls/Fence.png" width="960" height="780"/>
 <tile id="1">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="2">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="3">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="4">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="5">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="10">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="11">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="12">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="13">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="14">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="15">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="20">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="21">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="22">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="23">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="24">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="25">
  <properties>
   <property name="impassable" type="bool" value="true"/>
  </properties>
 </tile>
 <tile id="30" type="door">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="true"/>
   <property name="door" type="bool" value="true"/>
   <property name="impassable" type="bool" value="true"/>
   <property name="stateIds" value="{&quot;closed&quot;: 30, &quot;opened&quot;: 31}"/>
  </properties>
 </tile>
 <tile id="31" type="door">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="false"/>
   <property name="door" type="bool" value="true"/>
   <property name="impassable" type="bool" value="false"/>
   <property name="stateIds" value="{&quot;closed&quot;: 30, &quot;opened&quot;: 31}"/>
  </properties>
 </tile>
 <tile id="32" type="door">
  <properties>
   <property name="action" value="open"/>
   <property name="stateIds" value="{&quot;closed&quot;: 32, &quot;opened&quot;: 33}"/>
  </properties>
 </tile>
 <tile id="33" type="door">
  <properties>
   <property name="action" value="open"/>
   <property name="closed" type="bool" value="false"/>
   <property name="door" type="bool" value="false"/>
   <property name="impassable" type="bool" value="false"/>
   <property name="stateIds" value="{&quot;closed&quot;: 32, &quot;opened&quot;: 33}"/>
  </properties>
 </tile>
 <tile id="40">
  <properties>
   <property name="impassable" type="bool" value="false"/>
  </properties>
 </tile>
 <tile id="41">
  <properties>
   <property name="impassable" type="bool" value="false"/>
  </properties>
 </tile>
</tileset>
