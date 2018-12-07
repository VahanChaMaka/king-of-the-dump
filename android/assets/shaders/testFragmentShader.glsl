#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform vec2 texture_region_position;

// The inverse of the viewport dimensions along X and Y
uniform vec2 u_viewportInverse;

void main() {
    //float alpha_delta = texture2D(u_texture, v_texCoords).a;
    //vec2 local_coord = vec2(v_texCoord.x - texture_position.x, v_texCoord.y - texture_position.y);

    vec4 result = texture2D(u_texture, v_texCoords);
    //vec4 result = vec4(0, 0, 0, 1);

    if (v_texCoords.x < 0.5){
        result = vec4(1, 1, 1, 1);
    }

    gl_FragColor = v_color * result;

    /*if(v_texCoord.x == 0.1 || v_texCoord.y == 0.1){
         gl_FragColor = vec4(1, 1, 1, 1)* texture2D(u_texture, v_texCoord);
    } else if(v_texCoord.x == 1 && v_texCoord.y == 0){
        gl_FragColor = vec4(0, 1, 1, 1)* texture2D(u_texture, v_texCoord);
    } else if(v_texCoord.x == 0 && v_texCoord.y == 1){
              gl_FragColor = vec4(0, 0, 1, 1)* texture2D(u_texture, v_texCoord);
    } else if(v_texCoord.x == 1 && v_texCoord.y == 1){
              gl_FragColor = vec4(1, 0, 0, 1)* texture2D(u_texture, v_texCoord);
    } else {
        gl_FragColor = vec4(0, 0, 0, 1);
    }*/
}