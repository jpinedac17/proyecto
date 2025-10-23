# 🧪 Instrucciones para Probar el Envío de Datos al Webhook

## ✅ Cambios Implementados

### 1. Campo de Correo Agregado
- En `WelcomeActivity` ahora hay un campo de correo electrónico
- Se valida junto con los demás campos

### 2. Flujo de Datos
```
WelcomeActivity (captura: nombre, correo, edad, deporte)
    ↓
DashboardActivity (pasa los datos)
    ↓
StartTrainingActivity (pasa los datos al adapter)
    ↓
TiposAdapter (pasa los datos al seleccionar "Rutina personalizada")
    ↓
RutinaActivity (genera JSON y envía al webhook)
```

### 3. Botón que Envía Datos
**Botón:** "Personalizar rutina" (btn_personalizar_rutina)
**Ubicación:** RutinaActivity
**Acción:** Genera JSON y envía POST al webhook

## 🚀 Cómo Probar

### Paso 1: Sincronizar Gradle
1. Abre el proyecto en Android Studio
2. Verás un banner: "Gradle files have changed"
3. Click en **"Sync Now"**
4. Espera a que descargue OkHttp

### Paso 2: Ejecutar la App
1. Inicia sesión ingresando:
   - Nombre: "Carlos"
   - **Correo: "carlos@email.com"** ← NUEVO CAMPO
   - Edad: 24
   - Otros datos requeridos
   
2. Click en "¡Comenzar mi viaje!"

3. En Dashboard, click en "Iniciar entrenamiento"

4. Selecciona un deporte (ej: Gimnasio)

5. Selecciona "Rutina personalizada"

6. En la pantalla de rutina, click en **"Personalizar rutina"**

### Paso 3: Verificar el Envío
Verás 3 mensajes Toast:
1. ✅ "Enviando rutina personalizada..." (inmediato)
2. ✅ "¡Rutina enviada exitosamente! ✓" (si fue exitoso)
   O ❌ "Error al enviar datos: ..." (si hubo error)

### Paso 4: Ver los Logs
Abre **Logcat** en Android Studio y filtra por "RutinaActivity" para ver:
- JSON generado antes de enviar
- Código de respuesta del servidor
- Cuerpo de la respuesta

## 📋 Ejemplo de JSON Enviado

```json
{
  "nombre": "Carlos",
  "edad": 24,
  "correo": "carlos@email.com",
  "deporte": "Gimnasio",
  "rutina": [
    "Calentamiento - 5 min",
    "Sentadillas - 3 series × 15 repeticiones",
    "Flexiones - 3 series × 12 repeticiones",
    "Plancha - 3 min",
    "Burpees - 2 series × 10 repeticiones",
    "Estiramiento - 5 min"
  ]
}
```

## ⚠️ Notas Importantes

1. **Permiso de Internet:** Ya agregado en AndroidManifest.xml
2. **Dependencia OkHttp:** Ya agregada en build.gradle.kts (v4.12.0)
3. **Petición Asíncrona:** Se ejecuta en segundo plano para no bloquear la UI
4. **Manejo de Errores:** Captura errores de red y del servidor

## 🐛 Si No Funciona

### Error: "Cannot resolve symbol 'OkHttp'"
**Solución:** Sincroniza Gradle (File → Sync Project with Gradle Files)

### Error de Red
**Solución:** Verifica que el emulador/dispositivo tenga conexión a Internet

### No aparece el campo de correo
**Solución:** Verifica que `activity_welcome.xml` tenga el campo `et_correo`

### Los datos llegan vacíos
**Solución:** Asegúrate de seguir todo el flujo desde WelcomeActivity

---

✅ **Todo está listo para funcionar. Solo necesitas sincronizar Gradle y probar la app.**

