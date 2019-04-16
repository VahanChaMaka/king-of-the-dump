#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D u_texture;

// The inverse of the viewport dimensions along X and Y
uniform vec2 u_viewportInverse;

// Color of the outline
uniform vec4 u_color;

// Thickness of the outline
uniform float u_offset;

// Step to check for neighbors
uniform float u_step;

uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

#define ALPHA_VALUE_BORDER 0.5

void main() {
   vec2 T = v_texCoords.xy;

   float alpha = 0.0;
   bool allin = true;
   for( float ix = -u_offset; ix < u_offset; ix += u_step )
      {
         for( float iy = -u_offset; iy < u_offset; iy += u_step )
          {
             float newAlpha = texture2D(u_texture, T + vec2(ix, iy) * u_viewportInverse).a;
             allin = allin && newAlpha > ALPHA_VALUE_BORDER;
             if (newAlpha > ALPHA_VALUE_BORDER && newAlpha >= alpha)
             {
                alpha = newAlpha;
             }
         }
      }
   if (!allin)
   {
      //alpha = 0.0;
      gl_FragColor = u_color;
      gl_FragColor.a = alpha;
   } else {
        gl_FragColor = texture2D(u_texture, v_texCoords);
   }
}