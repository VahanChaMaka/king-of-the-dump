#ifdef GL_ES
    precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoord;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

#define ALPHA_VALUE_BORDER 0.5

void main() {
    float alpha_delta = 0;
    float next_x = v_texCoord.x + 10;
    float next_y = v_texCoord.y;

    alpha_delta = texture2D(u_texture, v_texCoord).a - texture2D(u_texture, v_texCoord + vec2(next_x, next_y)).a;

    if(alpha_delta == 1){
        vec3 u_color = vec3(1.0, 0.0, 0.0);
        gl_FragColor = vec4(u_color, 1) * texture2D(u_texture, v_texCoord);
    } else {
        gl_FragColor = v_color * texture2D(u_texture, v_texCoord);
    }
}
